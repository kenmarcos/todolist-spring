package br.com.kenmarcos.todolistspring.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.kenmarcos.todolistspring.models.TaskModel;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    List<TaskModel> findByUserId(UUID userId);
    // TaskModel findByIdAndByUserId(UUID id, UUID userId);
}
