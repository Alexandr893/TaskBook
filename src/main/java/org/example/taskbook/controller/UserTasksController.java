package org.example.taskbook.controller;

import lombok.AllArgsConstructor;
import org.example.taskbook.dto.CommentDto;
import org.example.taskbook.enums.Status;
import org.example.taskbook.service.UserTaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/")
@AllArgsConstructor
public class UserTasksController {

    private UserTaskService userTaskService;

    @PostMapping("/{taskId}/status")
    public ResponseEntity<?> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestParam Status newStatus,
            @RequestParam String username) {

        return userTaskService.updateTaskStatus(taskId, newStatus, username);
    }


    @PostMapping("/{taskId}/comments")
    public ResponseEntity<?> addCommentToTask(
            @PathVariable Long taskId,
            @RequestBody CommentDto commentDTO,
            @RequestParam String username) {

        return userTaskService.addCommentToTask(taskId, commentDTO, username);
    }
}
