/**
 * @introduce
 * <p>
 *     <ul>
 *         <li>모든 글 조회에 필요한 dto</li>
 *         <li>PostsResponseDTO.java</li>
 *     </ul>
 * </p>
 * <p>variables
 * <ul>
 *     <li>id</li>
 *     <li>title</li>
 *     <li>content</li>
 *     <li>nickname</li>
 * </ul>
 * </p>
 *
 * @author Hwang junsik
 */

package com.blog.lastdance.dto.postdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostsResponseDTO {

    private Long id;
    private String title;
    private String content;
    private String nickname;

}
