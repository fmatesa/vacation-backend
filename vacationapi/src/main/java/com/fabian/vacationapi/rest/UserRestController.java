package com.fabian.vacationapi.rest;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fabian.vacationapi.dao.AccountRepository;
import com.fabian.vacationapi.dao.AuthorityRepository;
import com.fabian.vacationapi.dao.UserRepository;
import com.fabian.vacationapi.email.EmailServiceImpl;
import com.fabian.vacationapi.entity.Account;
import com.fabian.vacationapi.entity.Authority;
import com.fabian.vacationapi.entity.User;
import com.fabian.vacationapi.http.AccountPackage;


@RestController
@RequestMapping("auth/")
@CrossOrigin(origins="http://localhost:4200")
public class UserRestController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private AuthorityRepository authorityRepository;
	@Autowired
	private EmailServiceImpl emailService;
	
	@PostMapping("login")
	public String login(@RequestBody User userRequest) {
		Optional<User> user = userRepository.login(userRequest.getEmail(), userRequest.getPassword());
        if(user.isPresent()){
            String token = UUID.randomUUID().toString();
            User custom= user.get();
            custom.setToken(token);
            userRepository.save(custom);
            return token;
        }

        else return "";
	}
	
	@PostMapping("signup")
	public String signup(@RequestBody AccountPackage accountPackage){
		System.out.print(accountPackage);
		if(userRepository.findByEmail(accountPackage.getEmail()).isEmpty()) {
			User newUser = new User(accountPackage.getEmail(), accountPackage.getPassword());
			Account newAccount = new Account(accountPackage.getEmail(), accountPackage.getFirstName(), accountPackage.getLastName());
			Authority newAuthority = new Authority(accountPackage.getEmail());
			userRepository.save(newUser);
			accountRepository.save(newAccount);
			authorityRepository.save(newAuthority);
			return "success";
		}
		else return "account already exists";		
	}
	
	@PostMapping("logout")
	public String logout(@RequestBody String token) {
		Optional<User> user =userRepository.findByToken(token);
		if(user.isPresent()) {
			User loggedOut = user.get();
			loggedOut.setToken(null);
			userRepository.save(loggedOut);
			return "success";
		}
		else throw new RuntimeException("invalid token");
	}
	
	@PostMapping("admin")
	public String admin(@RequestBody String token) {
		Optional<User> user =userRepository.findByToken(token);
		if(user.isPresent()) {
			Authority auth = authorityRepository.findByEmail(user.get().getEmail());
			if(auth.isAdmin()) return "true";
		}
		return "";
	}
	
	@PostMapping("mail")
	public String mail(@RequestBody String email) {
		Optional<User> user =userRepository.findByEmail(email);
		if(user.isPresent()) {
			String password = user.get().getPassword();
			this.emailService.sendSimpleMessage(email, "Forgot password", "Your password is: "+password);
			return "email sent";
		}
		return "no account with that email found";
	}
}
