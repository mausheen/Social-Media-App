package com.socialmeadia.app.repository;

import com.socialmeadia.app.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepo extends JpaRepository<Notification,Integer> {
}
