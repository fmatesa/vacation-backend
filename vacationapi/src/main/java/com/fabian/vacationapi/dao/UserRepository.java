package com.fabian.vacationapi.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fabian.vacationapi.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query(value = "SELECT u FROM User u where u.email = ?1 and u.password = ?2 ")
    Optional<User> login(String username,String password);
    Optional<User> findByToken(String token);
	Optional<User> findByEmail(String email);
}
