package com.fabian.vacationapi.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fabian.vacationapi.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

	public Account findByEmail(String email);
}
