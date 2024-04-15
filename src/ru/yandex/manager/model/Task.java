package ru.yandex.manager.model;

import java.util.Objects;

public class Task {
    public int id;
    public String title;
    public String description;
    public Status status;

    public Task(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = Status.NEW;
    }

    // Геттеры для получения и сеттеры для установки идентификатора, заголовка, описания, статуса задачи
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // Переопределение для сравнения задач по их идентификаторам
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Task task = (Task) o;
        return id == task.id;
    }

    // Переопределение для получения хэш-кода задачи на основе идентификатора
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Переопределение для представления информации о задаче в виде строки
    @Override
    public String toString() {
        return "Task {" +
                "id = " + id +
                ", title = '" + title + '\'' +
                ", description = '" + description + '\'' +
                ", status = " + status +
                '}';
    }
}