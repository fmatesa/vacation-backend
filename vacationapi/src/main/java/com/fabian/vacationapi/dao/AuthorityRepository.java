package com.fabian.vacationapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fabian.vacationapi.entity.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
	public Authority findByEmail(String email);
}
