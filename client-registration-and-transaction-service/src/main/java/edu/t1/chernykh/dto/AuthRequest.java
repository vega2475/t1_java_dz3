package edu.t1.chernykh.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(@NotBlank
                            @Size(max = 20)
                            String login,
                          @NotBlank
                            @Size(max = 50)
                            String password) {
}