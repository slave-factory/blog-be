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
public class MembersDTO {

    private Integer id;
    private String nickname;
    private String userId;
    private String password;
    private String passwordConfirm;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Members toEntity(PasswordEncoder encoder) {
        Members members = new Members();
        members.setNickname(this.nickname);
        members.setUserId(this.userId);
        members.setPassword(encoder.encode(this.password));
        members.setCreatedAt(this.createdAt);
        members.setUpdatedAt(this.updatedAt);
        return members;
    }

    public UsernamePasswordAuthenticationToken toAuthToken() {
        return new UsernamePasswordAuthenticationToken(userId, password);
    }
}
