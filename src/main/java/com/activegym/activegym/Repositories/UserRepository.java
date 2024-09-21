package com.activegym.activegym.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.activegym.activegym.Entities.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>{
    Optional<User> findByDocument(String document);
}
