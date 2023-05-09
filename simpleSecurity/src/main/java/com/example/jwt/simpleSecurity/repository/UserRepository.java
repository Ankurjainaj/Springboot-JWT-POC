package com.example.jwt.simpleSecurity.repository;

import com.example.jwt.simpleSecurity.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query(value = "{email:?0}")
    Optional<User> findByEmail(String email);

    @Query(value = "{email:?0}")
    Page<User> findByEmailContains(String email, Pageable pageable);
    @Query(value = "{email:?0}")
    Page<User> findAllByEmail(String email, Pageable pageable);
    @Query(value = "{email:?0}")
    Page<User> findAllByEmailContainsAndEmail(String email, String auth, Pageable pageable);

    @Query(value = "{email:?0}", exists = true)
    Boolean existsByEmail(String email);
}
