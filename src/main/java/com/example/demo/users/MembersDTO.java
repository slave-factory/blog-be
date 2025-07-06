package com.example.demo.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    private LocalDateTime createdAt;

    public static MembersDTO toMembersDTO(Members members) {
        MembersDTO membersDTO = new MembersDTO();
        membersDTO.setId(members.getId());
        membersDTO.setNickname(members.getNickname());
        membersDTO.setUserId(members.getUserId());
        membersDTO.setPassword(members.getPassword());
        membersDTO.setCreatedAt(members.getCreatedAt());

        return membersDTO;
    }
}
