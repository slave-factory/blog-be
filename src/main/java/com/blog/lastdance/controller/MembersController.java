/**
 *
 * @introduce
 *  - 회원 기능 관리를 위한 restController<br>
 *  - 모든 rest api 규칙 : /api/**<br>
 *
 * MembersController.java
 *
 * @author Hwang junsik
 */
package com.blog.lastdance.controller;

import com.blog.lastdance.detail.CustomMemberDetails;
import com.blog.lastdance.dto.memberdto.LoginDTO;
import com.blog.lastdance.dto.memberdto.SignupDTO;
import com.blog.lastdance.exception.CustomException;
import com.blog.lastdance.service.MembersService;
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

    /**
     * 회원가입 요청을 처리하는 메서드 (/api/signup)
     * <hr>
     * <p>
     * detail
     * <ul>
     *     <li>비밀번호와 비밀번호 확인이 일치하는지 검증 (불일치 시 예외 발생)</li>
     *     <li>아이디 또는 별명이 이미 존재하는지 확인 (중복 시 예외 발생)</li>
     *     <li>모든 검증이 통과하면 회원가입을 완료하고 성공 메시지 반환</li>
     * </ul>
     * </p>
     *
     * @param signupDTO 회원가입에 필요한 정보가 담긴 DTO
     * @return
     * <ul>
     *     <li>success: <code>{"message": "회원가입 성공"}</code></li>
     *     <li>fail:
     *         <ul>
     *             <li>비밀번호 불일치: <code>{"error": "2개의 비밀번호가 일치하지 않습니다"}</code></li>
     *             <li>아이디/별명 중복: <code>{"error": "이미 존재하는 아이디/별명입니다"}</code></li>
     *         </ul>
     *     </li>
     * </ul>
     */

    @PostMapping("/signup") // POST /api/signup
    public ResponseEntity<?> signup(@RequestBody SignupDTO signupDTO) { // 요청을 SignDTO 형식으로 보내야 함

        // 비밀번호 확인 - 비밀번호 비교
        if(!signupDTO.getPassword().equals(signupDTO.getPasswordConfirm())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of(
                        "error", "2개의 비밀번호가 일치하지 않습니다"
                    ));
        }

        // 아이디 or 별명 존재 확인
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
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "회원가입 성공"
        ));
    }

    /**
     * 로그인 요청을 처리하는 메서드 (/api/login)
     * <hr>
     * <p>
     *     detail
     *     <ul>
     *         <li>아이디 비번 유효성 검사(불일치 시 예외 발생)</li>
     *         <li>세션 생성 & 저장</li>
     *         <li>모든 검증이 완료되면 로그인 성공하고 세션을 포함한 성공 메세지 반환</li>
     *     </ul>
     * </p>
     *
     * @param loginDTO 로그인에 필요한 정보가 담긴 dto
     * @param request 세션 생성을 위한 httpServletRequest
     * @return
     * <ul>
     *     <li>success: <pre>{@code
     *     {
     *          "message": "로그인 성공",
     *          "nickname": "등록한 별명",
     *          "id": 등록된 기본키
     *     }
     *     }</pre></li>
     *     <li>fail:
     *     <ul>
     *         <li>아이디 비번 불일치: <code>{"error": "아이디 또는 비밀번호가 잘못됨"}</code></li>
     *     </ul>
     *     </li>
     *
     * </ul>
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        try{
            // 아이디 비번 유효성 검사
            Authentication auth = authenticationManager.authenticate(loginDTO.toAuthToken());
            // 인증 정보 저장
            SecurityContextHolder.getContext().setAuthentication(auth);
            // 세션 생성
            HttpSession session = request.getSession(true);
            // SecurityContext 저장
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

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

    /**
     * 로그아웃 요청을 처리하는 메서드 (/api/logout)
     * <hr>
     * <p>
     *     detail
     *     <ul>
     *         <li>세션 제거</li>
     *         <li>정상적으로 세션이 제거되었다면 성공 메세지 반환</li>
     *     </ul>
     * </p>
     * @param request 세션 제거를 위한 httpServletRequest
     * @return
     * <ul>
     *     <li>success: <code>{"message": "로그아웃 성공"}</code></li>
     * </ul>
     */
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
