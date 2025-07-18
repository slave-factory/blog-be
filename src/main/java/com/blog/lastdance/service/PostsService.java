/**
 * @introduce
 * <p>
 *     detail
 *     <ul>
 *         <li>글 요청 관리에 필요한 service</li>
 *     </ul>
 * </p>
 *
 * @author Hwang junsik
 */

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

    /**
     * 글을 작성하는 메서드
     * <hr>
     * <p>
     *     detail
     *     <ul>
     *         <li>postRepository를 활용해 작성한 글 등록</li>
     *     </ul>
     * </p>
     * @param postsRequestDTO 글 작성의 요청 dto
     * @return 글의 기본키 반환
     */
    public Long createPosts(PostsRequestDTO postsRequestDTO) {

        // 에러 메세지 없음
        Posts post = postsRequestDTO.toEntity();
        postsRepository.save(post);

        return post.getId();
    }

    /**
     * 글을 수정하는 메서드
     * <hr>
     * <p>
     *     detail
     *     <ul>
     *         <li>기본키를 통해 데이터베이스에 해당 글 조회(글이 존재하지 않을 경우 예외 발생)</li>
     *         <li>글이 존재한다면 요청한 내용으로 글 수정</li>
     *     </ul>
     * </p>
     * @param id 글의 기본키
     * @param postsRequestDTO 글 수정의 요청 dto
     */
    public void putPosts(Long id, PostsRequestDTO postsRequestDTO) {

        Posts post = postsRepository.findById(id).orElse(null);

        if(post == null) {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다");
        }

        post.update(postsRequestDTO);
        postsRepository.save(post);

    }

    /**
     * 글을 삭제하는 메서드
     * <hr>
     * <p>
     *     detail
     *     <ul>
     *         <li>기본키를 통해 데이터베이스에 해당 글 조회(글이 존재하지 않을 경우 예외 발생)</li>
     *         <li>글이 존재한다면 해당 글 데이터베이스에서 삭제</li>
     *     </ul>
     * </p>
     * @param id 삭제할 글의 기본키
     */
    public void deletePosts(Long id) {

        Posts post = postsRepository.findById(id).orElse(null);

        if(post == null) {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다");
        }

        postsRepository.delete(post);

    }

    /**
     * 모든 글 조회하기 요청을 처리하는 메서드
     * <hr>
     * <p>
     *     detail
     *     <ul>
     *         <li>데이터베이스에 존재하는 모든 글을 불러와 리스트 형식으로 반환</li>
     *     </ul>
     * </p>
     * @return list 형태로 존재하는 모든 글 반환
     *
     */
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

    /**
     * 특정 글 조회하기 요청을 처리하는 메서드
     * <hr>
     * <p>
     *     detail
     *     <ul>
     *         <li>전달받은 글의 기본키를 활용해 해당 글 검색(존재하지 않을 경우 예외 발생)</li>
     *         <li>존재할 경우 해당 글의 저자를 조회하여 글의 내용과 같이 반환</li>
     *     </ul>
     * </p>
     * @param id 조회하고 싶은 글의 기본키
     * @return 글의 정보와 글쓴이의 별명을 담은 postDetail dto를 반환
     */
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
