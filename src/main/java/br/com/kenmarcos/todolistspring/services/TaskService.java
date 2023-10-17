package br.com.kenmarcos.todolistspring.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.kenmarcos.todolistspring.errors.AppException;
import br.com.kenmarcos.todolistspring.models.TaskModel;
import br.com.kenmarcos.todolistspring.repositories.ITaskRepository;
import br.com.kenmarcos.todolistspring.utils.Utils;

@Service
public class TaskService {
    @Autowired
    private ITaskRepository taskRepository;

    public TaskModel create(TaskModel taskModel) {

        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            throw new AppException("A data de início / data de término deve ser maior do que a data atual",
                    HttpStatus.BAD_REQUEST);
        }

        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            throw new AppException("A data de início deve ser maior do que a data de fim", HttpStatus.BAD_REQUEST);
        }

        var task = this.taskRepository.save(taskModel);

        return task;
    }

    public List<TaskModel> list(UUID userId) {
        var tasks = this.taskRepository.findByUserId(userId);

        return tasks;
    }

    public TaskModel update(UUID taskId, UUID userId, TaskModel taskModel) {
        var task = this.taskRepository.findById(taskId).orElse(null);

        if (task == null) {
            throw new AppException("A tarefa nao existe", HttpStatus.BAD_REQUEST);
        }

        if (!task.getUserId().equals(userId)) {
            throw new AppException("A tarefa não pertence ao usuário", HttpStatus.BAD_REQUEST);
        }

        Utils.copyNonNullProperties(taskModel, task);

        var updatedTask = this.taskRepository.save(task);

        return updatedTask;

    }
}
