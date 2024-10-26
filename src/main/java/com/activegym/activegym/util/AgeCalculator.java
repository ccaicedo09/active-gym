package com.activegym.activegym.util;


import com.activegym.activegym.model.Users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

/**
 * Utility class for calculating the age of a user.
 * This class contains the business logic for setting and calculating the age based on the user's date of birth.
 *
 * @since v1.0
 * @author Carlos Esteban Castro Caicedo
 */
@Service
@RequiredArgsConstructor
public class AgeCalculator {

    /**
     * Sets the age of the user based on their date of birth.
     *
     * @param user the user whose age is to be set.
     */
    public void setAge(User user) {
        if (user.getDateOfBirth() != null) {
            user.setAge(calculateAge(user.getDateOfBirth()));
        }
    }

    /**
     * Calculates the age based on the given date of birth.
     *
     * @param dateOfBirth the date of birth to calculate the age from.
     * @return the calculated age.
     */
    private int calculateAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}
