package com.example.myspringbootplayground.count;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/count")
public class CountController {
    @Autowired
    CountService countService;

    @PostMapping
    public Count createCount() {
        return countService.create();
    }

    @PostMapping("{id}/decrease-with-condition")
    public void decreaseWithCondition(@PathVariable Long id) {
        countService.decreaseWithCondition(id);
    }

    @PostMapping("{id}/decrease-lock")
    public Count decreaseLock(@PathVariable Long id) throws InterruptedException {
        return countService.decreaseLock(id);
    }
}
