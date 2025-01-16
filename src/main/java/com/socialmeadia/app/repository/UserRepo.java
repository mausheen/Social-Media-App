package com.socialmeadia.app.repository;

import com.socialmeadia.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User,Integer> {

    @Query("SELECT u FROM User u JOIN Post p ON u.userId=p.userId GROUP BY u.userId ORDER BY SUM(p.likes) DESC LIMIT 1")
    User findUserWithMostLikes();
}
