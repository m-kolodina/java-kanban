package ru.yandex.manager.service;

import ru.yandex.manager.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private int id = 1;

    private final Map<Integer, SubTask> subTasks;

    private final Map<Integer, Epic> epics;

    private final Map<Integer, Task> tasks;

    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        subTasks = new HashMap<>();
        epics = new HashMap<>();
        tasks = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
    }

    @Override
    public void addNewSubtask(SubTask subTask) {
        subTask.setId(id++);
        subTasks.put(subTask.getId(), subTask);
        subTask.getEpic().addSubTask(subTask);
        setEpicProgressStatus(subTask.getEpic());
    }

    @Override
    public void addNewEpic(Epic epic) {
        epic.setId(id++);
        epics.put(epic.getId(), epic);
    }

    @Override
    public void addNewTask(Task task) {
        task.setId(id++);
        tasks.put(task.getId(), task);
    }

    @Override
    public List<SubTask> getSubtasks() {
        return subTasks.values().stream().toList();
    }

    @Override
    public List<Epic> getEpics() {
        return epics.values().stream().toList();
    }

    @Override
    public List<Task> getTasks() {
        return tasks.values().stream().toList();
    }

    @Override
    public void deleteAllSubtasks() {
        subTasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public SubTask getSubtask(int id) {
        historyManager.add(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Task getTask(int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public void updateSubtask(SubTask subTask, int id) {
        subTasks.put(id, subTask);
        Epic epic = subTask.getEpic();
        epic.setSubtasks(subTasks.values().stream().filter(x -> x.getEpic().equals(epic)).toList());
        setEpicProgressStatus(subTask.getEpic());
    }

    @Override
    public void updateEpic(Epic epic, int id) {
        epics.put(id, epic);
    }

    @Override
    public void updateTask(Task task, int id) {
        tasks.put(id, task);
    }

    @Override
    public void deleteEpicFromId(int id) {
        epics.remove(id);
    }

    @Override
    public void deleteSubtaskFromId(int id) {
        subTasks.remove(id);
    }

    @Override
    public void deleteTaskFromId(int id) {
        tasks.remove(id);
    }

    @Override
    public List<SubTask> getEpicSubtasks(int id) {
        return epics.get(id).getSubtasks();
    }

    private void setEpicProgressStatus(Epic epic) {
        List<SubTask> epicSubTasks = epic.getSubtasks();
        boolean statusNew = epicSubTasks.stream().allMatch(x -> x.getTaskProgressStatus() == TaskProgressStatus.NEW);
        boolean statusDone = epicSubTasks.stream().allMatch(x -> x.getTaskProgressStatus() == TaskProgressStatus.DONE);
        if (statusDone) {
            epic.setTaskProgressStatus(TaskProgressStatus.DONE);
        } else if (statusNew) {
            epic.setTaskProgressStatus(TaskProgressStatus.NEW);
        } else {
            epic.setTaskProgressStatus(TaskProgressStatus.IN_PROGRESS);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}