package com.example.to_do.controller;

import com.example.to_do.DTO.TaskDto;
import com.example.to_do.entity.Task;
import com.example.to_do.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {


    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/add")
    public ResponseEntity<Task> addTask(@RequestBody TaskDto taskDto){
        return ResponseEntity.ok(taskService.addTask(taskDto));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Task>> getAllTasks(){
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(@RequestBody TaskDto taskDto, @PathVariable Long id){
        return ResponseEntity.ok(taskService.updateTask(taskDto,id));
    }

    @PatchMapping("/updatestatus/{id}")
    public ResponseEntity<String> updateStatus(@RequestBody TaskDto taskDto,@PathVariable Long id){
        return ResponseEntity.ok(taskService.updateStatus(taskDto,id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
         return ResponseEntity.noContent().build();
    }

}
