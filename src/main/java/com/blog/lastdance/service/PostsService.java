package com.blog.lastdance.service;

import com.blog.lastdance.dto.postdto.PostsDetailDTO;
import com.blog.lastdance.dto.postdto.PostsRequestDTO;
import com.blog.lastdance.dto.postdto.PostsResponseDTO;
import com.blog.lastdance.entity.Members;
import com.blog.lastdance.entity.Posts;
import com.blog.lastdance.repository.MembersRepository;
import com.blog.lastdance.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final MembersRepository membersRepository;

    public Long createPosts(PostsRequestDTO postsRequestDTO) {

        // 에러 메세지 없음
        Posts post = postsRequestDTO.toEntity();
        postsRepository.save(post);

        return post.getId();
    }

    public void putPosts(Long id, PostsRequestDTO postsRequestDTO) {

        Posts post = postsRepository.findById(id).orElse(null);

        if(post == null) {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다");
        }

        post.update(postsRequestDTO);
        postsRepository.save(post);

    }

    public void deletePosts(Long id) {

        Posts post = postsRepository.findById(id).orElse(null);

        if(post == null) {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다");
        }

        postsRepository.delete(post);

    }

    public List<PostsResponseDTO> findAllPosts() {

        List<Posts> posts = postsRepository.findAll();
        List<PostsResponseDTO> postsResponseDTO = new ArrayList<>();

        for(Posts post : posts) {

            Members members = membersRepository.findById(post.getAuthorId());

            PostsResponseDTO dto = new PostsResponseDTO();
            dto.setId(post.getId());
            dto.setTitle(post.getTitle());
            dto.setContent(post.getContent());
            dto.setNickname(members.getNickname());

            postsResponseDTO.add(dto);
        }

        return postsResponseDTO;
    }

    public PostsDetailDTO findById(Long id) {

        Posts post = postsRepository.findById(id).orElse(null);

        if(post == null) {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다");
        }

        Members members = membersRepository.findById(post.getAuthorId());
        PostsDetailDTO dto = new PostsDetailDTO();

        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setNickname(members.getNickname());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());

        return dto;

    }
}
