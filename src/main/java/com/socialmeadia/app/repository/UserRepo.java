package com.socialmeadia.app.repository;

import com.socialmeadia.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
}
