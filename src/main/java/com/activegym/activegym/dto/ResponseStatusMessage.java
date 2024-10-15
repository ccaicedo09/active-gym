package com.activegym.activegym.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class ResponseStatusMessage {
    private String message;
}
