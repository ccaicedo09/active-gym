package com.activegym.activegym.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for formatting date and time in a more readable way.
 * This class contains the business logic for formatting LocalDateTime objects to a more human-readable format.
 * @author Carlos Esteban Castro Caicedo
 * @since v1.3
 */
@Service
@RequiredArgsConstructor
public class FormatDateTime {
    public String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedDate = dateTime.format(dateFormatter);
        String formattedTime = dateTime.format(timeFormatter);
        return formattedDate + " a las " + formattedTime;
    }
}
