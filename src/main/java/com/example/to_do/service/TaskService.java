package com.example.to_do.service;

import com.example.to_do.DTO.TaskDto;
import com.example.to_do.MapStruct.TaskMapper;
import com.example.to_do.entity.Task;
import com.example.to_do.entity.User;
import com.example.to_do.repository.TaskRepository;
import com.example.to_do.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {


    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userRepository = userRepository;
    }

    public Task addTask(TaskDto taskDto) {

        Task task = taskMapper.convertToEntity(taskDto);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        task.setUser(user);
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks()
    {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return taskRepository.findByUser(user);
    }

    public Task updateTask(TaskDto taskDto, Long id) {
       Task oldTask =  taskRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Task not Found")
        );

        User currentUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        // check if task belongs to this user
        if (!oldTask.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You cannot update another user's task");
        }

       oldTask.setTitle(taskDto.getTitle());
       oldTask.setDescription(taskDto.getDescription());
       oldTask.setPriority(taskDto.getPriority());
       oldTask.setStatus(taskDto.getStatus());

       return taskRepository.save(oldTask);
    }

    public void deleteTask(Long id) {

        User currentUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Task task = taskRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new RuntimeException("Task not found for this user"));

        taskRepository.delete(task);
    }

    public String updateStatus(TaskDto taskDto, Long id) {
        User currentUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Task oldTask = taskRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        oldTask.setStatus(taskDto.getStatus());
        taskRepository.save(oldTask);

        return "Task status updated successfully";
    }
}
