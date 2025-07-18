/**
 * @introduce
 * <p>
 *     <ul>
 *         <li>회원가입에 필요한 dto</li>
 *         <li>SignupDTO.java</li>
 *     </ul>
 * </p>
 * <p>variables
 * <ul>
 *     <li>nickname</li>
 *     <li>userId</li>
 *     <li>password</li>
 *     <li>passwordConfirm</li>
 * </ul>
 * </p>
 *
 * @author Hwang junsik
 */

package com.blog.lastdance.dto.memberdto;

import com.blog.lastdance.entity.Members;
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
public class SignupDTO {

    private String nickname;
    private String userId;
    private String password;
    private String passwordConfirm;

    /**
     * dto를 엔티티로 변환하는 메서드
     * @param encoder 비밀번호를 암호화하기 위한 passwordEncoder
     * @return dto를 엔티티로 만들어서 반환(Members)
     */
    public Members toEntity(PasswordEncoder encoder) {
        Members members = new Members();
        members.setNickname(this.nickname);
        members.setUserId(this.userId);
        members.setPassword(encoder.encode(this.password));
        members.setCreatedAt(LocalDateTime.now());
        members.setUpdatedAt(LocalDateTime.now());
        return members;
    }

    public UsernamePasswordAuthenticationToken toAuthToken() {
        return new UsernamePasswordAuthenticationToken(userId, password);
    }
}
