package com.example.myspringbootplayground.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagService tagService;

    @Transactional
    public Post createPost(String title) {
        Post post = new Post();
        post.setTitle(title);
        post.setStatus(PostStatus.DRAFT);
        post = postRepository.save(post);
        tagService.createTag(post.getId(), title);
        return post;
    }

    @Transactional
    public Post publishPost(Long id) throws InterruptedException {
        System.out.println("publishPost: start postRepository.findByIdLock");
        Post post = postRepository.findByIdLock(id).orElseThrow();
        System.out.println("publishPost: get post data from postRepository.findByIdLock");
        tagService.updateTag(id, post.getTitle());
        post.setStatus(PostStatus.PUBLISHED);
        post = postRepository.save(post);
        System.out.println("publishPost: commit");
        return post;
    }

    @Transactional
    public Post unpublishPost(Long id) throws InterruptedException {
        System.out.println("unpublishPost: start postRepository.findByIdLock");
        Post post = postRepository.findByIdLock(id).orElseThrow();
        System.out.println("unpublishPost: get post data from postRepository.findByIdLock");
        tagService.getTag(id);
        post.setStatus(PostStatus.UNPUBLISHED);
        post = postRepository.save(post);
        System.out.println("unpublishPost: commit");
        return post;
    }
}
