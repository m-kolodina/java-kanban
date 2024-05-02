package ru.yandex.manager.model;

import java.util.Objects;

public class Task {

    private String name;
    private String description;
    private int id;
    private TaskProgressStatus taskProgressStatus;


    public Task(String name, String description, TaskProgressStatus taskProgressStatus) {
        this.name = name;
        this.description = description;
        this.taskProgressStatus = taskProgressStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public TaskProgressStatus getTaskProgressStatus() {
        return taskProgressStatus;
    }

    public void setTaskProgressStatus(TaskProgressStatus taskProgressStatus) {
        this.taskProgressStatus = taskProgressStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "model.Task: " + getName() + " Description: " + getDescription()
                + " Status: " + getTaskProgressStatus();
    }
}

