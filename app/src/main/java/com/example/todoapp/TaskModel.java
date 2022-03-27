package com.example.todoapp;

public class TaskModel {

    private long taskId;
    private String taskName;
    private int status;

    public TaskModel() {
    }

    public TaskModel(String taskName, int status) {
        this.taskName = taskName;
        this.status = status;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
