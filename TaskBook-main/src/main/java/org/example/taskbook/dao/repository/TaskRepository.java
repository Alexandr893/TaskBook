package org.example.taskbook.dao.repository;

import org.example.taskbook.dao.entity.Task;
import org.example.taskbook.dao.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByAuthor(User author, Pageable pageable);
    Page<Task> findByAssignee(User assignee,Pageable pageable);
}
