package com.fabian.vacationapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fabian.vacationapi.entity.Vacation;

@Repository
public interface VacationRepository extends JpaRepository<Vacation, Integer> {

}
