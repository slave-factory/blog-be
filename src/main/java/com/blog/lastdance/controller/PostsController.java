/**
 * @introduce
 *  - 글 기능 관리를 위한 restController<br>
 *  - 모든 rest api 규칙 : /api/**<br>
 *
 *  PostsController.java
 *
 * @author Hwang junsik
 */
package com.blog.lastdance.controller;

import com.blog.lastdance.dto.postdto.PostsDetailDTO;
import com.blog.lastdance.dto.postdto.PostsRequestDTO;
import com.blog.lastdance.dto.postdto.PostsResponseDTO;
import com.blog.lastdance.exception.CustomException;
import com.blog.lastdance.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class PostsController {

    private final PostsService postsService;

    /**
     * 글 업로드 요청을 처리하는 메서드 (/api/posts)
     * <hr>
     * <p>
     * detail
     * <ul>
     *     <li>작성한 글을 받아서 데이터베이스에 등록</li>
     * </ul>
     * </p>
     *
     * @param postsRequestDTO 글 작성에 필요한 정보가 담긴 dto
     * @return
     * <ul>
     *     <li>success: <pre>{@code
     *     {
     *          "message": "업로드 성공",
     *          "postId": 작성한 글의 기본키
     *     }
     *     }</pre></li>
     * </ul>
     */
    @PostMapping("/posts")
    public ResponseEntity<?> uploadPosts(@RequestBody PostsRequestDTO postsRequestDTO) {

        // POST 글 작성 등록
        Long postId = postsService.createPosts(postsRequestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "업로드 성공",
                        "postId", postId
                ));
    }

    /**
     * 글 수정 요청을 처리하는 메서드 (/api/posts/{id})
     * <hr>
     *     <p>
     *         detail
     *         <ul>
     *             <li>client가 소유한 post의 기본키를 이용해 수정(해당 글이 없다면 예외 발생)</li>
     *         </ul>
     *     </p>
     *
     * @param id 수정할 글의 기본키
     * @param postsRequestDTO 수정한 내용 dto
     * @return
     * <ul>
     * <li>success: <pre>{@code
     *     {
     *          "message": "수정 성공"
     *     }
     *     }</pre></li>
     * <li>fail: <pre>{@code
     *     {
     *         "error": "게시글을 찾을 수 없습니다"
     *     }
     * }</pre></li>
     * </ul>
     */
    @PutMapping("/posts/{id}")
    public ResponseEntity<?> updatePosts(@PathVariable Long id, @RequestBody PostsRequestDTO postsRequestDTO) {

        try{
            postsService.putPosts(id, postsRequestDTO);
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

    /**
     * 글 삭제 요청을 처리하는 메서드 (/api/posts/{id})
     * <hr>
     * <p>
     *     detail
     *     <ul>
     *         <li>client가 소유한 post의 기본키를 이용해 해당 글을 삭제(해당 글이 없다면 예외 발생)</li>
     *     </ul>
     * </p>
     * @param id 삭제할 글의 기본키
     * @return
     * <ul>
     *     <li>success: <pre>{@code
     *     {
     *         "message": "삭제 성공"
     *     }
     *     }</pre></li>
     *     <li>fail: <pre>{@code
     *     {
     *         "error": "게시글을 찾을 수 없습니다"
     *     }
     *     }</pre>
     *     </li>
     * </ul>
     */
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePosts(@PathVariable Long id) {

        try{
            postsService.deletePosts(id);
        }
        catch (IllegalArgumentException e){
            throw new CustomException(e.getMessage(), 404);
        }

        return ResponseEntity
                .status(200)
                .body(Map.of(
                        "message", "삭제 성공"
                ));

    }

    /**
     * 모든 글의 목록 보기 요청을 처리하는 메서드 (/api/posts)
     * <hr>
     * <p>
     *     detail
     *     <ul>
     *         <li>데이터베이스에 존재하는 모든 글을 리스트 형태로 반환</li>
     *     </ul>
     * </p>
     * @return
     * <ul>
     *     <li><pre>success: {@code
     *     [
     *          {
     *              "id": 1,
     *              "title": "제목1",
     *              "content": "내용1",
     *              "author": "별명1"
     *
     *          },
     *          {
     *              "id": 2,
     *              "title": "제목2",
     *              "content": "내용2",
     *              "author": "별명2"
     *          }
     *     ]
     *     }</pre></li>
     * </ul>
     */
    @GetMapping("/posts")
    public ResponseEntity<List<PostsResponseDTO>> getAllPosts() {

        List<PostsResponseDTO> postsResponseDTOList = postsService.findAllPosts();

        return ResponseEntity
                .status(200)
                .body(postsResponseDTOList);

    }

    /**
     * 특정 글 보기 요청을 처리하는 메서드 (/api/posts/{id})
     * <hr>
     * <p>
     *     detail
     *     <ul>
     *         <li>client가 가지고 있는 post의 기본키를 이용해 특정 글을 반환(글이 존재하지 않으면 예외 발생)</li>
     *     </ul>
     * </p>
     * @param id 조회할 특정 글의 기본키
     * @return
     * <ul>
     *     <li>success: <pre>{@code
     *     {
     *         "id": id,
     *         "title": "제목",
     *         "content": "내용",
     *         "author": "별명",
     *         "createdAt": 업로드 시간,
     *         "updatedAt": 수정 시간
     *     }
     *     }</pre></li>
     *     <li>fail: <pre>{@code
     *     {
     *          "error": "게시글을 찾을 수 없습니다"
     *     }
     *     }</pre></li>
     * </ul>
     */
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostsDetailDTO> getPosts(@PathVariable Long id) {

        try{
            PostsDetailDTO postsDetailDTO = postsService.findById(id);

            return ResponseEntity
                    .status(200)
                    .body(postsDetailDTO);
        }
        catch (IllegalArgumentException e){
            throw new CustomException(e.getMessage(), 404);
        }

    }

}
