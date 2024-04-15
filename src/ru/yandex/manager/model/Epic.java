package ru.yandex.manager.model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks;

    public Epic(int id, String title, String description) {
        super(id, title, description);
        this.subtasks = new ArrayList<>();
    }

    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    // Переопределение для представления информации об эпике в виде строки
    @Override
    public String toString() {
        return "Epic {" +
                "id = " + getId() +
                ", title = '" + getTitle() + '\'' +
                ", description = '" + getDescription() + '\'' +
                ", subtasks = " + subtasks +
                '}';
    }
}
