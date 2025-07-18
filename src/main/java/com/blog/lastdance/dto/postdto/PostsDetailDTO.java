/**
 * @introduce
 * <p>
 *     <ul>
 *         <li>특정 글을 조회하기 위해 필요한 dto</li>
 *         <li>PostsDetailDTO.java</li>
 *     </ul>
 * </p>
 * <p>variables
 * <ul>
 *     <li>id</li>
 *     <li>title</li>
 *     <li>content</li>
 *     <li>nickname</li>
 *     <li>createdAt</li>
 *     <li>updatedAt</li>
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

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostsDetailDTO {

    private Long id;
    private String title;
    private String content;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
