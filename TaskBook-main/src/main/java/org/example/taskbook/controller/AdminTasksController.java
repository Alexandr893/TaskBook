package org.example.taskbook.controller;

import lombok.AllArgsConstructor;
import org.example.taskbook.dao.entity.Task;
import org.example.taskbook.dto.CommentDto;
import org.example.taskbook.dto.TaskDto;
import org.example.taskbook.enums.Priority;
import org.example.taskbook.enums.Status;
import org.example.taskbook.service.AdminTasksService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminTasksController {


    private AdminTasksService adminTasksService;


    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskDto taskDto) {
        return adminTasksService.createTask(taskDto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        return adminTasksService.updateTask(id, taskDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        return adminTasksService.deleteTask(id);
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateTaskStatus(@PathVariable Long id, @RequestParam Status newStatus) {
        return adminTasksService.updateTaskStatus(id, newStatus);
    }


    @PutMapping("/{id}/priority")
    public ResponseEntity<?> updateTaskPriority(@PathVariable Long id, @RequestParam Priority newPriority) {
        return adminTasksService.updateTaskPriority(id, newPriority);
    }


    @PostMapping("/{taskId}/comments")
    public ResponseEntity<?> addComment(@PathVariable Long taskId, @RequestBody CommentDto commentDto) {
        return adminTasksService.addCommentToTask(taskId, commentDto);
    }


    @GetMapping("/")
    public ResponseEntity<?> getAllTasks() {
        return adminTasksService.getAllTasks();
    }

    // Получение задач по идентификатору автора


    @GetMapping("/author/{authorId}")
    public PagedModel<EntityModel<Task>> getTasksByAuthor(
            @PathVariable Long authorId,
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            PagedResourcesAssembler<Task> assembler) {

        Page<Task> page = adminTasksService.getTasksByAuthor(authorId, pageable);
        return assembler.toModel(page);
    }

    @GetMapping("/assignee/{assigneeId}")
    public PagedModel<EntityModel<Task>> getTasksByAssignee(
            @PathVariable Long assigneeId,
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            PagedResourcesAssembler<Task> assembler) {

        Page<Task> page = adminTasksService.getTasksByAssignee(assigneeId, pageable);
        return assembler.toModel(page);
    }
}
