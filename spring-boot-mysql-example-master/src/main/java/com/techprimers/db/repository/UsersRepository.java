package com.techprimers.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techprimers.db.model.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {
}
