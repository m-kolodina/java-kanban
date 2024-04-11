package ru.yandex.manager.model;

public class Epic extends Task {
    public Epic(int id, String title, String description) {
        super(id, title, description);
    }

    // Переопределение для представления информации об эпике в виде строки
    @Override
    public String toString() {
        return "Epic {" +
                "id = " + getId() +
                ", title = '" + getTitle() + '\'' +
                ", description = '" + getDescription() + '\'' +
                '}';
    }
}