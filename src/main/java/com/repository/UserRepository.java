package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Brand;
import com.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);
	
	@Query(value = "select b.id from operation.brand b inner join auth.user_brand ub on ub.brand_id = b.id inner join auth._user u on u.id = ub.user_id where u.id = :id ", nativeQuery = true)
	List<Long> getIdsBrandsById(@Param("id") Long id);
	@Query(value = "select pr.id from operation.project pr inner join auth.user_project up on up.project_id = pr.id inner join auth._user u on u.id = up.user_id where u.id = :id ", nativeQuery = true)
	List<Long> getIdsprojectsById(@Param("id") Long id);

}
