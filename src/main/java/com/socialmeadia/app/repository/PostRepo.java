package com.socialmeadia.app.repository;

import com.socialmeadia.app.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post,Integer> {

}
