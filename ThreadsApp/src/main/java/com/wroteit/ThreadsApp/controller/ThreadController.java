package com.wroteit.ThreadsApp.controller;

import com.wroteit.ThreadsApp.model.Thread;
import com.wroteit.ThreadsApp.service.ThreadService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/threads")
public class ThreadController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final ThreadService threadService;
    RestTemplate restTemplate;
    String baseUrl;

    @Autowired
    public ThreadController(ThreadService threadService) {
        this.threadService = threadService;
        restTemplate = new RestTemplate();
        baseUrl = "http://gatewayapp:8080";
    }

    @GetMapping
    public List<Thread> getAllThreads() {
        return threadService.getAllThreads();
    }

    @GetMapping("/{id}")
    public Thread getThreadById(@PathVariable String id) {
        return threadService.getThreadById(id);
    }

    @PostMapping
    public Thread createThread(@RequestBody Thread thread) {
        Boolean isBanned = restTemplate.getForObject(baseUrl + "/communities/" + thread.getAuthorId() + "/checkUserBanned/" + thread.getCommunityId(), Boolean.class);
        if(isBanned)return null;
        Thread createdThread = threadService.createThread(thread);
        return createdThread;
    }

    @PutMapping("/{id}")
    public Thread updateThread(@PathVariable String id, @RequestBody String threadDetails) {
        return threadService.updateThread(id, threadDetails);
    }

    @DeleteMapping("/{id}")
    public String deleteThread(@PathVariable String id) {
        return threadService.deleteThread(id);
    }

    @DeleteMapping("/ban/{id}")
    public String ban(@PathVariable String id) {
        return threadService.banThread(id);
    }

    @GetMapping("/author/{authorId}")
    public List<Thread> getThreadsByAuthorId(@PathVariable Long authorId) {
        return threadService.getThreadsByAuthorId(authorId);
    }

    @GetMapping("/community/{communityId}")
    public List<Thread> getThreadsByCommunityId(@PathVariable String communityId) {
        return threadService.getThreadsByCommunityId(communityId);
    }

    @PostMapping("/report/{threadId}")
    public String reportThread(@PathVariable String threadId, @RequestParam Long reporterId, @RequestParam String reason) {
        Thread thread = threadService.getThreadById(threadId);
        if (thread == null) return "Thread not found";

        Map<String, Object> reportData = new HashMap<>();
        reportData.put("communityId", thread.getCommunityId());
        reportData.put("reporterId", reporterId);
        reportData.put("reportedEntityId", threadId);
        reportData.put("entityType", "THREAD");
        reportData.put("reason", reason);

        rabbitTemplate.convertAndSend("moderator.exchange", "moderator.report", reportData);
        return "Report submitted";
    }




}


