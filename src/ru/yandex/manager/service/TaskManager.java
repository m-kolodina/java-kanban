package ru.yandex.manager.service;

import ru.yandex.manager.model.Epic;
import ru.yandex.manager.model.SubTask;
import ru.yandex.manager.model.Task;

import java.util.List;

public interface TaskManager {

    void addNewSubtask(SubTask subTask);

    void addNewEpic(Epic epic);

    void addNewTask(Task task);

    List<SubTask> getSubtasks();

    List<Epic> getEpics();

    List<Task> getTasks();

    void deleteAllSubtasks();

    void deleteAllEpics();

    void deleteAllTasks();

    SubTask getSubtask(int id);

    Epic getEpic(int id);

    Task getTask(int id);

    void updateSubtask(SubTask subTask, int id);

    void updateEpic(Epic epic, int id);

    void updateTask(Task task, int id);

    void deleteEpicFromId(int id);

    void deleteSubtaskFromId(int id);

    void deleteTaskFromId(int id);

    List<SubTask> getEpicSubtasks(int id);

    List<Task> getHistory();
}