package com.huchuchu.book.web;


import com.huchuchu.book.service.posts.PostsService;
import com.huchuchu.book.web.dto.PostsResponseDto;
import com.huchuchu.book.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save (@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    //수정or삭제
    @PostMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id ,@RequestBody PostsSaveRequestDto requestDto){
        return postsService.update(id,requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }




}
