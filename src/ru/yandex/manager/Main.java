package ru.yandex.manager;

import ru.yandex.manager.model.Epic;
import ru.yandex.manager.model.Status;
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

        Subtask subtask1 = new Subtask(1, "Подзадача №1", "Применить на практике советы Р. Мартина", epic1.getId());
        Subtask subtask2 = new Subtask(2, "Подзадача №2", "Сделать тестовое задание", epic1.getId());
        Subtask subtask3 = new Subtask(3, "Подзадача №3", "Восстановить режим", epic2.getId());

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
        task1.setTitle("Задача 1 обновлена");
        task1.setDescription("Покормить уток");
        task1.setStatus(Status.IN_PROGRESS);

        subtask1.setTitle("Подзадача 1 обновлена");
        subtask1.setDescription("Выспаться");

        epic1.setTitle("Эпик 1 обновлен");
        epic1.setDescription("Стать частицей фрактального всеединства существа");

        manager.updateTask(task1);
        manager.updateSubtask(subtask1);
        manager.updateEpic(epic1);

        System.out.println("Статусы после изменений:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }

        // Удаление задач, эпиков и подзадач
        manager.deleteTask(1);
        manager.deleteTask(2);
        manager.deleteEpic(epic2.getId());
        manager.deleteSubtask(subtask2.getId());

        System.out.println("Статусы после удаления:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }
    }
}
