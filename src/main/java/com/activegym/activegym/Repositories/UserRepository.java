package com.activegym.activegym.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.activegym.activegym.Entities.User;

public interface UserRepository extends CrudRepository<User, Long>{

}
