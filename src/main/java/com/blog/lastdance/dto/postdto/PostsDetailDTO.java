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
