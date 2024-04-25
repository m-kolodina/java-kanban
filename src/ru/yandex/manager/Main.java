package ru.yandex.manager;

import ru.yandex.manager.model.Epic;
import ru.yandex.manager.model.TaskProgressStatus;
import ru.yandex.manager.model.SubTask;
import ru.yandex.manager.model.Task;
import ru.yandex.manager.service.*;


public class Main {

    public static void main(String[] args) {

        TaskManager manager = Managers.getDefault();

        Task parrotTask = new Task("Покормить попугаев",
                "Нарвать ягод", TaskProgressStatus.DONE);
        Task mindTask = new Task("Поработать над концентрацией",
                "Достигнуть покоя и умиротворения", TaskProgressStatus.NEW);
        manager.addNewTask(parrotTask);
        manager.addNewTask(mindTask);

        Epic developmentEpic = new Epic("Развитие",
                "Применить на практике рекомендации Р. Мартина", TaskProgressStatus.IN_PROGRESS);
        Epic forceEpic = new Epic("Улучшить силовые показатели",
                "Регулярно посещать тренировки", TaskProgressStatus.DONE);
        manager.addNewEpic(developmentEpic);
        manager.addNewEpic(forceEpic);

        SubTask cleaningSubtask = new SubTask("Убраться дома",
                "Начать с уборки попугайской клетки", TaskProgressStatus.DONE, developmentEpic);
        SubTask clothSubtask = new SubTask("Разобрать зимнюю одежду",
                "Отдать пуховики в химчистку, а остальное убрать в шкаф",
                TaskProgressStatus.IN_PROGRESS, developmentEpic);
        SubTask runningSubtask = new SubTask("Найти новое место для пробежек",
                "Посмотреть на карте ближайшие парки и леса", TaskProgressStatus.NEW, forceEpic);
        manager.addNewSubtask(cleaningSubtask);
        manager.addNewSubtask(clothSubtask);
        manager.addNewSubtask(runningSubtask);

        printAllTasks(manager);
    }

    private static void printAllTasks(TaskManager manager) {

        manager.getTask(2);
        manager.getTask(1);
        manager.getEpic(4);
        manager.getSubtask(6);
        manager.getSubtask(7);

        System.out.println("Задачи:");
        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.getEpics()) {
            System.out.println(epic);

            for (Task task : manager.getEpicSubtasks(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}