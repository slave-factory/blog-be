package com.example.demo.users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MembersService {

    private final MembersRepository membersRepository;
    private PasswordEncoder passwordEncoder;

    public Members create(MembersDTO membersDTO) {
        Members members = new Members();
        members.setNickname(membersDTO.getNickname());
        members.setUserId(membersDTO.getUserId());
        members.setCreatedAt(membersDTO.getCreatedAt());

        // 암호화
        members.setPassword(passwordEncoder.encode(membersDTO.getPassword()));
        this.membersRepository.save(members);

        return members;
    }
}
