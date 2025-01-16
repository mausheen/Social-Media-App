package com.socialmeadia.app.repository;

import com.socialmeadia.app.model.QRCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QRCodeRepository extends JpaRepository<QRCode, Long> {
    QRCode findByData(String data);
}
