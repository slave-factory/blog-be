package com.example.demo.service;

import com.example.demo.dto.PostsDTO;
import com.example.demo.entity.Posts;
import com.example.demo.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    public Long createPosts(PostsDTO postsDTO) {

        // 에러 메세지 없음
        Posts post = postsDTO.toEntity();
        postsRepository.save(post);

        return post.getId();
    }

    public void putPosts(Long id, PostsDTO postsDTO) {

        Posts post = postsRepository.findById(id).orElse(null);

        if(post == null) {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다");
        }

        post.update(postsDTO);
        postsRepository.save(post);

    }

}
