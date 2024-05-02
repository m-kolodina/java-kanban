package ru.yandex.manager.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private List<SubTask> subtasks = new ArrayList<>();

    public Epic(String name, String description, TaskProgressStatus taskProgressStatus) {
        super(name, description, taskProgressStatus);
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

    public void clearSubTasks() {
        subtasks.clear();
    }


    @Override
    public String toString() {
        return "model.Epic: " + getName() + " Description: " + getDescription()
                + " Status: " + getTaskProgressStatus();
    }
}


