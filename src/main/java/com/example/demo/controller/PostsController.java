package com.example.demo.controller;

import com.example.demo.dto.PostsDTO;
import com.example.demo.exception.CustomException;
import com.example.demo.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class PostsController {

    private final PostsService postsService;

    @PostMapping("/posts")
    public ResponseEntity<?> uploadPosts(@RequestBody PostsDTO postsDTO) {

        // POST 글 작성 등록
        Long postId = postsService.createPosts(postsDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "업로드 성공",
                        "postId", postId
                ));
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<?> updatePosts(@PathVariable Long id, @RequestBody PostsDTO postsDTO) {

        try{
            postsService.putPosts(id, postsDTO);
        }
        catch (IllegalArgumentException e){
            throw new CustomException(e.getMessage(), 404);
        }

        return ResponseEntity
                .status(200)
                .body(Map.of(
                        "message", "수정 성공"
                ));

    }

}
