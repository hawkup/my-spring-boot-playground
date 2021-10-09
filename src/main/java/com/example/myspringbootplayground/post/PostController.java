package com.example.myspringbootplayground.post;

import com.example.myspringbootplayground.post.request.CreatePostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/post")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping
    public Post createPost(@RequestBody CreatePostRequest createPostRequest) {
        return postService.createPost(createPostRequest.getTitle());
    }

    @PostMapping("{id}/publish")
    public Post publishPost(@PathVariable Long id) throws InterruptedException {
        return postService.publishPost(id);
    }

    @PostMapping("{id}/unpublish")
    public Post unpublishPost(@PathVariable Long id) throws InterruptedException {
        return postService.unpublishPost(id);
    }
}
