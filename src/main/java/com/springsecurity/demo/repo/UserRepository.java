package com.springsecurity.demo.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springsecurity.demo.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{

	Boolean existsByEmail(String email);

	UserEntity findByEmailAndPassword(String username, String encode);

}
