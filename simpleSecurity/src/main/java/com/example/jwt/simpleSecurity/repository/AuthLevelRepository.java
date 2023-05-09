package com.example.jwt.simpleSecurity.repository;

import com.example.jwt.simpleSecurity.model.AuthLevel;
import com.example.jwt.simpleSecurity.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthLevelRepository extends MongoRepository<AuthLevel, String>, AuthLevelRepositoryCustom {
    Page<AuthLevel> findAllByUser(User user, Pageable pageable);
}
