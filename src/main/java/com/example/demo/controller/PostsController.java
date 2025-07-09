package com.example.demo.controller;

import com.example.demo.dto.PostsDTO;
import com.example.demo.entity.Posts;
import com.example.demo.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class PostsController {

    private final PostsService postsService;

    @PostMapping("/posts")
    public ResponseEntity<?> uploadPosts(@RequestBody PostsDTO postsDTO) {

        // POST 글 작성 등록
        Posts posts = postsService.createPosts(postsDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "업로드 성공",
                        "postId", posts.getId()
                ));
    }
}
