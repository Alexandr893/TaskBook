package org.example.taskbook.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.example.taskbook.enums.Priority;
import org.example.taskbook.enums.Status;

@Getter
@Setter
public class TaskDto {
    private Long id;
    @NotBlank(message = "Укажите заголовок")
    private String title;
    @NotBlank(message = "описание")
    private String description;
    private String username;
    private Long authorId;
    private Long assigneeId;
    private Status status;
    private Priority priority;
}
