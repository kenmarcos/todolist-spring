package br.com.kenmarcos.todolistspring.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.kenmarcos.todolistspring.models.TaskModel;
import br.com.kenmarcos.todolistspring.services.TaskService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        var userId = request.getAttribute("userId");
        taskModel.setUserId((UUID) userId);

        var newTask = taskService.create(taskModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

    @GetMapping("/")
    public ResponseEntity list(HttpServletRequest request) {
        var userId = request.getAttribute("userId");

        var tasks = taskService.list((UUID) userId);

        return ResponseEntity.status(HttpStatus.OK).body(tasks);

    }

    @PutMapping("/{taskId}")
    public ResponseEntity update(@RequestBody TaskModel taskModel,
            HttpServletRequest request,
            @PathVariable UUID taskId) {

        var userId = request.getAttribute("userId");

        var updatedTask = taskService.update(taskId, (UUID) userId, taskModel);

        return ResponseEntity.status(HttpStatus.OK).body(updatedTask);

    }
}
