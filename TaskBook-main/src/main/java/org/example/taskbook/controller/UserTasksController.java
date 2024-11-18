package org.example.taskbook.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.example.taskbook.dto.CommentDto;
import org.example.taskbook.enums.Status;
import org.example.taskbook.service.UserTaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/api/user/")
@AllArgsConstructor
public class UserTasksController {

    private UserTaskService userTaskService;

    @Operation(summary = "Обновить статус задачи ", description = "доступно только пользователю")
    @PostMapping("/{taskId}/status")
    public ResponseEntity<?> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestParam Status newStatus,
            @RequestParam String username) {

        return userTaskService.updateTaskStatus(taskId, newStatus, username);
    }

    @Operation(summary = "Добавить комментарий к задаче ", description = "доступно только пользователю")
    @PostMapping("/{taskId}/comments")
    public ResponseEntity<?> addCommentToTask(
            @PathVariable Long taskId,
            @RequestBody CommentDto commentDTO,
            @RequestParam String username) {

        return userTaskService.addCommentToTask(taskId, commentDTO, username);
    }
}
