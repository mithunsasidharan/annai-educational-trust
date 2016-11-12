package com.ohack.aet.repository;

import org.springframework.data.repository.CrudRepository;

import com.ohack.aet.model.User;

public interface UserMongoRepository extends CrudRepository<User, String>{
	
	public User findByAadharNo(String aadharNo);
}