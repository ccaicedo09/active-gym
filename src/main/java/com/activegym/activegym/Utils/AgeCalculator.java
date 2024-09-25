package com.activegym.activegym.Utils;


import com.activegym.activegym.Entities.Users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class AgeCalculator {

    public void setAge(User user) {
        if (user.getDateOfBirth() != null) {
            user.setAge(calculateAge(user.getDateOfBirth()));
        }
    }

    private int calculateAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}
