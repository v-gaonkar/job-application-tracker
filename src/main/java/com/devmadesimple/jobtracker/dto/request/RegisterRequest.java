package com.devmadesimple.jobtracker.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank
    @Size( min=3, max=30)
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can contain only letters, numbers and underscore")
    private String username;

    @NotBlank
    @Email
    @Size(max=254)
    private String email;


    @NotBlank
    @Size(min=6, max=64)
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=.,?]).+$",
            message = "Password must contain uppercase, lowercase, number and special character"
    )
    private String password;
}
