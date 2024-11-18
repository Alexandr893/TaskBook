package org.example.taskbook.service;

import lombok.AllArgsConstructor;
import org.example.taskbook.dao.entity.Comment;
import org.example.taskbook.dao.entity.Task;
import org.example.taskbook.dao.entity.User;
import org.example.taskbook.dao.repository.CommentRepository;
import org.example.taskbook.dao.repository.TaskRepository;
import org.example.taskbook.dao.repository.UserRepository;
import org.example.taskbook.dto.CommentDto;
import org.example.taskbook.enums.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserTaskService {

    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private CommentRepository commentRepository;



    public ResponseEntity<?> updateTaskStatus(Long taskId, Status newStatus, String username) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        User user = findUserByEmail(username);

        if (isUserAssignee(task,user)) {
            task.setStatus(newStatus);
            taskRepository.save(task);
            return ResponseEntity.ok("Task status updated");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: Not the assignee");
        }
    }



    public ResponseEntity<?> addCommentToTask(Long taskId, CommentDto commentDTO, String username) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }

         User user = findUserByEmail(username);

        if (isUserAssignee(task,user)) {
            Comment comment = new Comment();
            comment.setText(commentDTO.getText());
            comment.setAuthor(user);
            comment.setTask(task);
            commentRepository.save(comment);
            return ResponseEntity.ok("Comment added");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: Not the assignee");
        }
    }


    public User findUserByEmail(String username) {
        Optional<User> userOptional = userRepository.findByEmail(username);
        return userOptional.orElseThrow(() -> new RuntimeException("User not found with email: " + username));
    }

    //Проверка того, назначена ли задача указанному пользователю
    private boolean isUserAssignee(Task task, User user) {
        return task.getAssignee() != null && task.getAssignee().getId().equals(user.getId());
    }


}
