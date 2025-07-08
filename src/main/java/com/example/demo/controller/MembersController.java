package com.example.demo.controller;

import com.example.demo.detail.CustomMemberDetails;
import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.SignupDTO;
import com.example.demo.exception.CustomException;
import com.example.demo.service.MembersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class MembersController {

    private final MembersService membersService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup") // POST /api/signup
    public ResponseEntity<?> signup(@RequestBody SignupDTO signupDTO) { // 요청을 SignDTO 형식으로 보내야 함

        // 비밀번호 확인 - 비밀번호 비교
        if(!signupDTO.getPassword().equals(signupDTO.getPasswordConfirm())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of(
                        "message", "2개의 비밀번호가 일치하지 않습니다"
                    ));
        }

        // 아이디 존재 확인
        try{
            membersService.create(signupDTO);
        }
        catch(IllegalArgumentException e) {
            // 409 conflict "이미 존재하는 아이디입니다" or "이미 존재하는 별명입니다"
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "error", e.getMessage()
                    ));
        }

        // 201 created "회원가입 성공"
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "회원가입 성공"
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        try{
            // 아이디 비번 유효성 검사
            Authentication auth = authenticationManager.authenticate(loginDTO.toAuthToken());
            SecurityContextHolder.getContext().setAuthentication(auth);
            // 세션 생성
            request.getSession(true);

            CustomMemberDetails memberDetails = (CustomMemberDetails) auth.getPrincipal();
            return ResponseEntity.ok(Map.of(
                    "message", "로그인 성공",
                    "nickname", memberDetails.getNickname(),
                    "id", memberDetails.getId()
            ));

        } catch (Exception e) {
            throw new CustomException("아이디 또는 비밀번호가 잘못됨", 401); // 401 unauthorized

        }

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok(Map.of(
                "message", "로그아웃 성공"
        ));
    }
}
