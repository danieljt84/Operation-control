package com.repository.operation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Holyday;

public interface HolydayRepository extends JpaRepository<Holyday, Long> {

	List<Holyday> findByDateBetween(LocalDate start,LocalDate end);
}
