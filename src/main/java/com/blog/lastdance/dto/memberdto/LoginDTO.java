/**
 * @introduce
 *  - 로그인에 필요한 dto<br>
 *  - LoginDTO.java
 *
 * <p>variables
 *     <ul>
 *         <li>userId</li>
 *         <li>password</li>
 *     </ul>
 * </p>
 * @author Hwang junsik
 */
package com.blog.lastdance.dto.memberdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoginDTO {

    private String userId;
    private String password;

    public UsernamePasswordAuthenticationToken toAuthToken() {
        return new UsernamePasswordAuthenticationToken(userId, password);
    }
}
