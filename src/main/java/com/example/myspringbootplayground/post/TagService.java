package com.example.myspringbootplayground.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PostRepository postRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Tag getTag(Long postId) throws InterruptedException {
        System.out.println("getTag: start tagRepository.findByPostId");
        Tag tag = tagRepository.findByPostId(postId).orElseThrow();
        System.out.println("getTag: get tags data from tagRepository.findByPostId");
        System.out.println("getTag: sleep");
        Thread.sleep(5000);
        System.out.println("getTag: finish");
        return tag;
    }

    @Transactional
    public void createTag(Long postId, String name) {
        Tag tag = new Tag();
        Post post = postRepository.findById(postId).orElseThrow();
        tag.setName(name);
        tag.setPost(post);
        tagRepository.save(tag);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateTag(Long postId, String name) throws InterruptedException {
        System.out.println("upsertTag: start tagRepository.findByPostIdLock");
        Tag tag = tagRepository.findByPostIdLock(postId).orElseThrow();
        System.out.println("upsertTag: get tag data from tagRepository.findByPostIdLock");
        Post post = postRepository.findById(postId).orElseThrow();
        tag.setName(name);
        tag.setPost(post);
        System.out.println("upsertTag: sleep");
        Thread.sleep(5000);
        tagRepository.save(tag);
        System.out.println("upsertTag: commit");
    }
}
