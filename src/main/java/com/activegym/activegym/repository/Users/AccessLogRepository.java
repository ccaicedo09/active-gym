package com.activegym.activegym.repository.Users;

import com.activegym.activegym.model.Users.AccessLog;
import com.activegym.activegym.model.Users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
    Page<AccessLog> findAll(Pageable pageable);
    Page<AccessLog> findAllByUserId(User user, Pageable pageable);
}
