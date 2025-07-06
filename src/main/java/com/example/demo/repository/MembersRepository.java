package com.example.demo.repository;

import com.example.demo.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembersRepository extends JpaRepository<Members, Integer> {
    Optional<Members> findByUserId(String userId);
}
