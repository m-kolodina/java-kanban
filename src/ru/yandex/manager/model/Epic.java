package ru.yandex.manager.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private String description;
    private List<SubTask> subtasks = new ArrayList<>();

    public Epic(String name, String description, TaskProgressStatus taskProgressStatus) {
        super(name, description, taskProgressStatus);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SubTask> getSubtasks() {
        return subtasks;
    }

    public void addSubTask(SubTask subTask) {
        subtasks.add(subTask);
    }

    public void setSubtasks(List<SubTask> subtasks) {
        this.subtasks = subtasks;
    }

    @Override
    public String toString() {
        return "model.Epic: " + getName() + " Description: " + getDescription()
                + " Status: " + getTaskProgressStatus();
    }
}

