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
