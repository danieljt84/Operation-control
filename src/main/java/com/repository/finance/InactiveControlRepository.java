package com.repository.finance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.finance.InactiveControl;

public interface InactiveControlRepository extends JpaRepository<InactiveControl, Long> {

	List<InactiveControl> findByDataActivityId(Long idDataActivity);
}
