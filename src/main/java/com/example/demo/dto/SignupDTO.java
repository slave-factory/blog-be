package com.example.demo.dto;

import com.example.demo.entity.Members;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SignupDTO {

    private String nickname;
    private String userId;
    private String password;
    private String passwordConfirm;

    public Members toEntity(PasswordEncoder encoder) {
        Members members = new Members();
        members.setNickname(this.nickname);
        members.setUserId(this.userId);
        members.setPassword(encoder.encode(this.password));
        members.setCreatedAt(LocalDateTime.now());
        members.setUpdatedAt(LocalDateTime.now());
        return members;
    }

    public UsernamePasswordAuthenticationToken toAuthToken() {
        return new UsernamePasswordAuthenticationToken(userId, password);
    }
}
