package com.socialmeadia.app.controller;

import com.socialmeadia.app.model.Notification;
import com.socialmeadia.app.model.Post;
import com.socialmeadia.app.model.User;
import com.socialmeadia.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    @PostMapping("/{userId}/posts")
    public ResponseEntity<Post> addPost(@PathVariable int userId, @RequestBody Post post) {
        return ResponseEntity.ok(userService.addPost(userId, post));
    }

    @PutMapping("/posts/{postId}/like")
    public ResponseEntity<String> likePost(@PathVariable int postId) {
        userService.likePost(postId);
        return new ResponseEntity<>("Post like successfully !!", HttpStatus.OK);
    }

    @PostMapping("/posts/{postId}/notify")
    public ResponseEntity<Notification> sendNotification(@PathVariable int postId) {
        return ResponseEntity.ok(userService.sendNotification(postId));
    }

    @GetMapping("/most-notifications")
    public ResponseEntity<User> getUserWithMostNotifications() {
        return ResponseEntity.ok(userService.getUserWithMostNotifications());
    }

    @GetMapping("/most-likes")
    public ResponseEntity<User> getUserWithMostLikes() {
        return ResponseEntity.ok(userService.getUserWithMostLikes());
    }
}
