package com.repository.finance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.finance.Bonus;

@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {

}
