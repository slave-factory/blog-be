package com.example.demo.entity;

import com.example.demo.dto.MembersDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "members")
public class Members {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nickname", unique = true)
    private String nickname;

    @Column(name = "user_id", unique = true)
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public MembersDTO toDTO() {
        MembersDTO dto = new MembersDTO();
        dto.setId(this.id);
        dto.setUserId(this.userId);
        dto.setPassword(""); // password는 보안상 숨김
        dto.setNickname(this.nickname);
        dto.setCreatedAt(this.createdAt);
        dto.setUpdatedAt(this.updatedAt);
        return dto;
    }

}
