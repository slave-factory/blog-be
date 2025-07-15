/**
 * @introduce
 *  - 글 업로드 등에 사용되는 dto <br>
 *  - PostsDTO.java
 *
 *
 *
 * @author Hwang junsik
 */

package com.blog.lastdance.dto.postdto;

import com.blog.lastdance.entity.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostsRequestDTO {

    private Long authorId;
    private String title;
    private String content;

    public Posts toEntity() {
        Posts posts = new Posts();
        posts.setAuthorId(authorId);
        posts.setTitle(this.title);
        posts.setContent(this.content);
        posts.setCreatedAt(LocalDateTime.now());
        posts.setUpdatedAt(LocalDateTime.now());
        posts.setGreat(0);
        posts.setCommentCount(0);
        return posts;
    }

}
