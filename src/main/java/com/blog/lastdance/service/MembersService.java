/**
 *
 * @introduce
 * <p>detail
 *     <ul>
 *         <li>회원 관리에 필요한 service</li>
 *         <li>MembersService.java</li>
 *     </ul>
 * </p>
 *
 * @author Hwang junsik
 */

package com.blog.lastdance.service;

import com.blog.lastdance.dto.memberdto.SignupDTO;
import com.blog.lastdance.entity.Members;
import com.blog.lastdance.repository.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MembersService {

    private final MembersRepository membersRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 계정 생성에 필요한 메서드
     * <hr>
     * <p>detail
     *      <ul>
     *          <li>기존 데이터베이스에 존재하는 아이디인지 확인(존재할 시 예외 반환)</li>
     *          <li>기존 데이터베이스에 존재하는 별명인지 확인(존재할 시 예외 반환)</li>
     *          <li>예외가 발생하지 않으면 데이터베이스에 저장</li>
     *      </ul>
     * </p>
     * @param signupDTO 회원가입에 필요한 member dto
     */
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
