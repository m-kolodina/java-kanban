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
        if (epics.containsValue(subTask.getEpic())) {
            subTask.setId(id++);
            subTasks.put(subTask.getId(), subTask);
            subTask.getEpic().addSubTask(subTask);
            setEpicProgressStatus(subTask.getEpic());
        } else {
            System.out.println("Ошибка: Эпик не найден. Подзадача не может быть добавлена.");
        }
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

        for (Epic epic : epics.values()) {
            epic.clearSubTasks();
            setEpicProgressStatus(epic);
        }
    }


    @Override
    public void deleteAllEpics() {
        epics.clear();
        subTasks.clear();
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
        if (subTasks.containsKey(id) && subTasks.get(id).getEpic().getId() == subTask.getEpic().getId()) {
            subTasks.put(id, subTask);
            setEpicProgressStatus(subTask.getEpic());
        }
    }

    @Override
    public void updateEpic(Epic epic, int id) {
        if (epics.containsKey(id)) {
            Epic existingEpic = epics.get(id);
            existingEpic.setDescription(epic.getDescription());
            existingEpic.setName(epic.getName());
        }
    }

    @Override
    public void updateTask(Task task, int id) {
        if (tasks.containsKey(id)) {
            tasks.put(id, task);
        } else {
            System.out.println("Ошибка: задачи с id " + id + " не существует, поэтому новая задача не была добавлена.");
        }
    }

    @Override
    public void deleteEpicFromId(int id) {
        if (epics.containsKey(id)) {
            Epic epicToDelete = epics.get(id);

            // Удаление всех подзадач, связанных с этим эпиком
            List<SubTask> subtasksToDelete = epicToDelete.getSubtasks();
            for (SubTask subtask : subtasksToDelete) {
                subTasks.remove(subtask.getId());
            }

            // Удаление эпика
            epics.remove(id);
        }
    }


    @Override
    public void deleteSubtaskFromId(int id) {
        SubTask deletedSubtask = subTasks.remove(id);

        if (deletedSubtask != null) {
            for (Epic epic : epics.values()) {
                for (SubTask subTask : epic.getSubtasks()) {
                    if (subTask.getId() == id) {
                        epic.getSubtasks().remove(subTask);
                        setEpicProgressStatus(epic); // Пересчитываем статус Эпика
                        return;
                    }
                }
            }
        }
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

        if (epicSubTasks.isEmpty()) {
            epic.setTaskProgressStatus(TaskProgressStatus.NEW);
            return; // Завершаем метод, если нет подзадач
        }

        boolean statusDone = epicSubTasks.stream().allMatch(x -> x.getTaskProgressStatus() == TaskProgressStatus.DONE);
        boolean statusNew = epicSubTasks.stream().allMatch(x -> x.getTaskProgressStatus() == TaskProgressStatus.NEW);

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
