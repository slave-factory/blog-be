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

    public Posts createPosts(PostsDTO postsDTO) {

        // 에러 메세지 없음
        Posts post = postsDTO.toEntity();
        postsRepository.save(post);

        return post;
    }
}
