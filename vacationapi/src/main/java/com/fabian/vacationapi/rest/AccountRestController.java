package com.fabian.vacationapi.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fabian.vacationapi.dao.AccountRepository;
import com.fabian.vacationapi.dao.AuthorityRepository;
import com.fabian.vacationapi.dao.UserRepository;
import com.fabian.vacationapi.dao.VacationRepository;
import com.fabian.vacationapi.entity.Account;
import com.fabian.vacationapi.entity.Authority;
import com.fabian.vacationapi.entity.User;
import com.fabian.vacationapi.entity.Vacation;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins="http://localhost:4200")
public class AccountRestController {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private VacationRepository vacationRepository;
	
	@GetMapping("admin/{token}")
	public List<Account> getAccounts(@PathVariable String token){
		Optional<User> optionalUser = userRepository.findByToken(token);
		if(optionalUser.isPresent()) {
			String email = optionalUser.get().getEmail();
			if(authorityRepository.findByEmail(email).isAdmin())return this.accountRepository.findAll();
			else throw new RuntimeException("Unauthorized user");
		}
		else throw new RuntimeException("Invalid token");
	}
	
	@PutMapping("accounts/{token}")
	public Account updateAccount(@RequestBody Account theAccount, @PathVariable String token){
		Optional<User> optionalUser = userRepository.findByToken(token);
		if(optionalUser.isPresent()){
			String email = optionalUser.get().getEmail();
			if(authorityRepository.findByEmail(email).isAdmin() || 
			  (accountRepository.findByEmail(email).getAccountId()==theAccount.getAccountId() && antiHack(theAccount))) {
					if((!authorityRepository.findByEmail(email).isAdmin() || accountRepository.findByEmail(email).getAccountId()==theAccount.getAccountId()) && !theAccount.getEmail().equals(email)) {
						if(!userRepository.findByEmail(theAccount.getEmail()).isEmpty()) throw new RuntimeException("Email is already taken");
						User changed = userRepository.findByEmail(email).get();
						changed.setEmail(theAccount.getEmail());
						userRepository.save(changed);
						
						Authority alsoChanged = authorityRepository.findByEmail(email);
						alsoChanged.setEmail(theAccount.getEmail());
						authorityRepository.save(alsoChanged);
					}
					accountRepository.save(theAccount);
					return theAccount;
			}
		}
		throw new RuntimeException("Unauthorized");
		
	}
	
	@GetMapping("accounts/{token}")
	public Account getAccount(@PathVariable String token){
		Optional<User> optionalUser = userRepository.findByToken(token);
		if(optionalUser.isPresent())
			{
				String email = optionalUser.get().getEmail();
				Account account = accountRepository.findByEmail(email);
				return account;
			}
		else throw new RuntimeException("Account not found");
	}
	
	private boolean antiHack(Account recievedAccount){
		Account requestedAccount = accountRepository.findById(recievedAccount.getAccountId()).get();
		if(!requestedAccount.getFirstName().equals(recievedAccount.getFirstName() ) || !requestedAccount.getLastName().equals(recievedAccount.getLastName()))return false;
		for(Vacation vacation : recievedAccount.getVacations()) {
			Optional<Vacation> requestedVacation = vacationRepository.findById(vacation.getId());
			if(vacation.isApproved() && (requestedVacation.isEmpty() || !requestedVacation.get().isApproved())) {
				System.out.println("Invalid update request sent to account: ");
				System.out.println(requestedAccount.toString());
				System.out.println("Recieved data: ");
				System.out.println(recievedAccount.toString());
				return false;
			}
			if(vacation.getId()!=0 && !requestedAccount.containsVacation(vacation.getId())) return false;
		}
		return true;
	}
}
