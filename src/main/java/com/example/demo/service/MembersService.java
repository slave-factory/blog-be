package com.example.demo.service;

import com.example.demo.dto.SignupDTO;
import com.example.demo.entity.Members;
import com.example.demo.repository.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MembersService {

    private final MembersRepository membersRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(SignupDTO signupDTO) {
        Members members = signupDTO.toEntity(passwordEncoder); // members 생성

        if(membersRepository.findByUserId(signupDTO.getUserId()).isPresent()) { // 아이디가 이미 존재한다면
            throw new IllegalArgumentException("이미 존재하는 아이디입니다");
        }
        else if(membersRepository.findByNickname(signupDTO.getNickname()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 별명입니다.");
        }

        this.membersRepository.save(members);

    }

}
