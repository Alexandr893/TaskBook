package org.example.taskbook.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/")
public class UserTasksController {

    @GetMapping("/test")
    public String userTasks() {
        return "Get user role success";
    }
}
