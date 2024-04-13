package ru.yandex.manager.service;

import ru.yandex.manager.model.Epic;
import ru.yandex.manager.model.Status;
import ru.yandex.manager.model.Subtask;
import ru.yandex.manager.model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, ArrayList<Integer>> epicSubtasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private int currentId = 0;

    public void addTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }

    public void addEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
    }

    public void addSubtask(int epicId, Subtask subtask) {
        subtask.setId(generateId());
        subtask.setEpicId(epicId);
        ArrayList<Integer> epicSubtaskList = epicSubtasks.computeIfAbsent(epicId, k -> new ArrayList<>());
        epicSubtaskList.add(subtask.getId());
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(epicId);
    }

    public void updateTask(Task taskToUpdate) {
        tasks.put(taskToUpdate.getId(), taskToUpdate);
    }

    public void updateEpic(Epic epicToUpdate) {
        Epic epic = epics.get(epicToUpdate.getId());
        if (epic != null) {
            epic.setTitle(epicToUpdate.getTitle());
            epic.setDescription(epicToUpdate.getDescription());
            updateEpicStatus(epicToUpdate.getId());
        }
    }

    public void updateSubtask(Subtask subtaskToUpdate) {
        subtasks.put(subtaskToUpdate.getId(), subtaskToUpdate);
        updateEpicStatus(subtaskToUpdate.getEpicId());
    }

    public void deleteTask(int taskId) {
        tasks.remove(taskId);
    }

    public void deleteEpic(int epicId) {
        epics.remove(epicId);
        epicSubtasks.remove(epicId);
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epicId) {
                subtasks.remove(subtask.getId());
            }
        }
    }

    public void deleteSubtask(int subtaskId) {
        Subtask subtask = subtasks.get(subtaskId);
        if (subtask != null) {
            int epicId = subtask.getEpicId();
            subtasks.remove(subtaskId);
            ArrayList<Integer> subtaskList = epicSubtasks.get(epicId);
            if (subtaskList != null) {
                subtaskList.remove(Integer.valueOf(subtaskId));
            }
            updateEpicStatus(epicId);
        }
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
        epicSubtasks.clear();
        subtasks.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        epicSubtasks.clear();
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Subtask> getSubtasksOfEpic(int epicId) {
        ArrayList<Integer> subtaskIds = epicSubtasks.get(epicId);
        ArrayList<Subtask> epicSubtasksList = new ArrayList<>();
        if (subtaskIds != null) {
            for (Integer subtaskId : subtaskIds) {
                epicSubtasksList.add(subtasks.get(subtaskId));
            }
        }
        return epicSubtasksList;
    }

    public int generateId() {
        return currentId++;
    }

    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            ArrayList<Subtask> subtasksOfEpic = getSubtasksOfEpic(epicId);
            boolean allSubtasksNew = true;
            for (Subtask subtask : subtasksOfEpic) {
                if (subtask.getStatus() != Status.NEW) {
                    allSubtasksNew = false;
                    break;
                }
            }
            if (allSubtasksNew || subtasksOfEpic.isEmpty()) {
                epic.setStatus(Status.NEW);
            } else {
                boolean allDone = true;
                for (Subtask subtask : subtasksOfEpic) {
                    if (subtask.getStatus() != Status.DONE) {
                        allDone = false;
                        break;
                    }
                }
                if (allDone) {
                    epic.setStatus(Status.DONE);
                } else {
                    epic.setStatus(Status.IN_PROGRESS);
                }
            }
        }
    }
}

