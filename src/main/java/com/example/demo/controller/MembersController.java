package com.example.demo.controller;

import com.example.demo.dto.MembersDTO;
import com.example.demo.service.MembersService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class MembersController {

    private final MembersService membersService;
    private AuthenticationManager authenticationManager; //아니 *발 왜 자꾸 오류가

    @PostMapping("/signup") // POST /api/signup
    public ResponseEntity<?> signup(@RequestBody MembersDTO membersDTO) { // 요청을 membersDTO 형식으로 보내야 함

        // 비밀번호 확인 - 비밀번호 비교
        if(!membersDTO.getPassword().equals(membersDTO.getPasswordConfirm())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("2개의 비밀번호가 일치하지 않습니다");
        }

        // 아이디 존재 확인
        try{
            membersService.create(membersDTO);
        }
        catch(IllegalArgumentException e) {
            // 409 conflict "이미 존재하는 아이디입니다"
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }

        // 201 created "회원가입 성공"
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }

    @PostMapping("/sign")
    public ResponseEntity<?> login(@RequestBody MembersDTO membersDTO, HttpServletRequest request) {
        Authentication auth = authenticationManager.authenticate(membersDTO.toAuthToken());
        SecurityContextHolder.getContext().setAuthentication(auth);
        // 세션 생성
        request.getSession(true);

        return ResponseEntity.ok("로그인 성공");
    }
}
