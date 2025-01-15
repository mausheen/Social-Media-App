package com.socialmeadia.app.service;

import com.socialmeadia.app.model.Notification;
import com.socialmeadia.app.model.Post;
import com.socialmeadia.app.model.User;
import com.socialmeadia.app.repository.NotificationRepo;
import com.socialmeadia.app.repository.PostRepo;
import com.socialmeadia.app.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private NotificationRepo notificationRepository;

    @Autowired
    private PostRepo postRepository;

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public Post addPost(int userId, Post post) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            post.setUser(user.get());
            post.setTime(LocalDateTime.now());
            return postRepository.save(post);
        }
        throw new RuntimeException("User not found");
    }

    public void likePost(int postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            Post existingPost = post.get();
            existingPost.setLikes(existingPost.getLikes() + 1);
            postRepository.save(existingPost);

            // Create Notification
            Notification notification = new Notification();
            notification.setPost(existingPost);
            notification.setUser(existingPost.getUser());
            notification.setTime(LocalDateTime.now());
            notificationRepository.save(notification);
        } else {
            throw new RuntimeException("Post not found");
        }
    }

    public User getUserWithMostNotifications() {
        return userRepository.findAll().stream()
                .max(Comparator.comparingInt(user -> user.getNotifications().size()))
                .orElse(null);
    }

    public User getUserWithMostLikes() {
        return userRepository.findAll().stream()
                .max(Comparator.comparingInt(user -> user.getPosts().stream().mapToInt(Post::getLikes).sum()))
                .orElse(null);
    }
    public Notification sendNotification(int postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            Post existingPost = post.get();
            User postUser = existingPost.getUser();

            Notification notification = new Notification();
            notification.setPost(existingPost);
            notification.setUser(postUser);
            notification.setTime(LocalDateTime.now());

            return notificationRepository.save(notification);
        }
        throw new RuntimeException("Post not found");
    }
}
