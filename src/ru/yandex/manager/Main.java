package ru.yandex.manager;

import ru.yandex.manager.model.Epic;
import ru.yandex.manager.model.Subtask;
import ru.yandex.manager.model.Task;
import ru.yandex.manager.service.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        Task task1 = new Task(1, "Задача №1", "Покормить попугаев");
        Task task2 = new Task(2, "Задача №2", "Выйти на пробежку");
        Epic epic1 = new Epic(1, "Эпик №1", "Обрести покой и умиротворение в этом бесконечно вечном");

        // Добавление задач и эпиков в менеджер
        manager.addTask(task1);
        manager.addTask(task2);
        manager.addEpic(epic1);

        Epic epic2 = new Epic(2, "Эпик №2", "Поработать над концентрацией");

        manager.addEpic(epic2);

        Subtask subtask1 = new Subtask(manager.generateId(), "Подзадача №1", "Применить на практике советы Р. Мартина", epic1.getId());
        Subtask subtask2 = new Subtask(manager.generateId(), "Подзадача №2", "Сделать тестовое задание", epic1.getId());
        Subtask subtask3 = new Subtask(manager.generateId(), "Подзадача №3", "Восстановить режим", epic2.getId());

        // Добавление подзадач к соответствующим эпикам
        manager.addSubtask(epic1.getId(), subtask1);
        manager.addSubtask(epic1.getId(), subtask2);
        manager.addSubtask(epic2.getId(), subtask3);

        System.out.println("Список задач и эпиков:");

        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }

        for (Epic epic : manager.getAllEpics()) {
            System.out.println(epic);
        }

        // Обновление задач, подзадач и эпиков
        manager.updateTask(task1);
        manager.updateTask(task2);
        manager.updateSubtask(new Subtask(1, "Подзадача NEW", "Посмотреть вебинар", epic1.getId()));
        manager.updateEpic(epic1); // Обновляем информацию об epic1 (его заголовок и описание) и обновляем его статус на основе статусов его подзадач

        System.out.println("Статусы после изменений:");

        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }

        // Удаление задач, эпиков и подзадач
        manager.deleteTask(1);
        manager.deleteEpic(epic2.getId());
        manager.deleteSubtask(subtask2.getId());

        System.out.println("Статусы после удаления:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }
    }
}

