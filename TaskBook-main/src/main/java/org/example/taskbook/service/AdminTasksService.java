package org.example.taskbook.service;

import lombok.AllArgsConstructor;
import org.example.taskbook.dao.entity.Comment;
import org.example.taskbook.dao.entity.Task;
import org.example.taskbook.dao.entity.User;
import org.example.taskbook.dao.repository.CommentRepository;
import org.example.taskbook.dao.repository.TaskRepository;
import org.example.taskbook.dao.repository.UserRepository;
import org.example.taskbook.dto.CommentDto;
import org.example.taskbook.dto.TaskDto;
import org.example.taskbook.enums.Priority;
import org.example.taskbook.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminTasksService {

    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;

    public ResponseEntity<?> createTask(TaskDto taskDto) {
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());
        task.setPriority(taskDto.getPriority());
        task.setAssignee(userRepository.findById(taskDto.getAssigneeId()).orElse(null));
        task.setAuthor(userRepository.findById(taskDto.getAuthorId()).orElse(null));
        taskRepository.save(task);
        return ResponseEntity.ok("Task created successfully");
    }

    public ResponseEntity<?> updateTask(Long taskId, TaskDto taskDto) {
        Task task = taskRepository.findById(taskId).orElse(null);

        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setPriority(taskDto.getPriority());
        task.setAssignee(userRepository.findById(taskDto.getAssigneeId()).orElse(null));
        taskRepository.save(task);
        return ResponseEntity.ok("Task updated successfully");
    }

    public ResponseEntity<?> deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElse(null);

        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }

        taskRepository.delete(task);
        return ResponseEntity.ok("Task deleted successfully");
    }

    public ResponseEntity<?> updateTaskStatus(Long taskId, Status newStatus) {
        Task task = taskRepository.findById(taskId).orElse(null);

        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }

        task.setStatus(newStatus);
        taskRepository.save(task);
        return ResponseEntity.ok("Task status updated");
    }

    public ResponseEntity<?> updateTaskPriority(Long taskId, Priority newPriority) {
        Task task = taskRepository.findById(taskId).orElse(null);

        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }

        task.setPriority(newPriority);
        taskRepository.save(task);
        return ResponseEntity.ok("Task priority updated");
    }


    public ResponseEntity<?> addCommentToTask(Long taskId, CommentDto commentDto) {
        Task task = taskRepository.findById(taskId).orElse(null);

        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }

        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        comment.setAuthor(userRepository.findById(commentDto.getAuthorId()).orElse(null));
        comment.setTask(task);
        commentRepository.save(comment);
        return ResponseEntity.ok("Comment added");
    }

    public ResponseEntity<?> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return ResponseEntity.ok(tasks);
    }

    public Page<Task> getTasksByAuthor(Long authorId, Pageable pageable) {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + authorId));
        return  taskRepository.findByAuthor(author, pageable);
    }

    public Page<Task> getTasksByAssignee(Long assigneeId, Pageable pageable) {
        User assignee = userRepository.findById(assigneeId)
                .orElseThrow(() -> new RuntimeException("Assignee not found with id: " + assigneeId));
        return taskRepository.findByAssignee(assignee, pageable);
    }
}
