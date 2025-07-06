package com.example.demo.users;

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

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static Members toMembers(MembersDTO membersDTO) {
        Members members = new Members();
        members.setId(membersDTO.getId());
        members.setNickname(membersDTO.getNickname());
        members.setPassword(membersDTO.getPassword());
        members.setCreatedAt(membersDTO.getCreatedAt());

        return members;
    }

}
