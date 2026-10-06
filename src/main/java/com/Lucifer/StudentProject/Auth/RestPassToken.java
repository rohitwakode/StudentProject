package com.Lucifer.StudentProject.Auth;

public record RestPassToken(
        String email,
        String resetToken,
        String newPassword
) {
}
