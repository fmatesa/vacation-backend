package com.fabian.vacationapi.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="account")
public class Account {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="account_id")
	private int accountId;
	
	@Column(name="email")
	private String email;
	
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@OneToMany(fetch=FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="account_id")
	private Set<Vacation> vacations;


	public Account() {

	}

	public Account(String email, String firstName, String lastName) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int id) {
		this.accountId = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<Vacation> getVacations() {
		return vacations;
	}

	public void setVacations(Set<Vacation> vacations) {
		this.vacations = vacations;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", vacations=" + vacations + "]";
	}

	public boolean containsVacation(int vacationId) {
		boolean x = false;
		for(Vacation vacation : this.vacations) {
			if(vacation.getId()==vacationId)x=true;
		}
		return x;
	}
	
	
	
}
