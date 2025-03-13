package com.cv.resume.creator.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cv.resume.creator.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);

}
