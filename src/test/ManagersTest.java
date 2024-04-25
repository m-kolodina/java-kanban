package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.manager.model.Epic;
import ru.yandex.manager.model.SubTask;
import ru.yandex.manager.model.Task;
import ru.yandex.manager.service.Managers;
import ru.yandex.manager.service.TaskManager;

import static ru.yandex.manager.model.TaskProgressStatus.IN_PROGRESS;
import static ru.yandex.manager.model.TaskProgressStatus.NEW;

class ManagersTest {

    private TaskManager manager;
    private Task task;
    private Epic epic;
    private SubTask subTask;

    @BeforeEach
    public void newManager() {
        manager = Managers.getDefault();
        task = new Task("New Task", "New task description", NEW);
        epic = new Epic("new epic", "new epic descripion", NEW);
        subTask = new SubTask("New Subtask", "New Subtask description", NEW, epic);
        manager.addNewTask(task);
        manager.addNewSubtask(subTask);
        manager.addNewEpic(epic);
    }

    @Test
    public void managerObjectsNotNull() {
        Assertions.assertNotNull(Managers.getDefault(), "Объект менеджера не инициализирован");
        Assertions.assertNotNull(Managers.getDefaultHistory(), "Объект менеджера истории не инициализирован");
    }

    @Test
    public void inMemoryTaskManagerFindFromId() {
        Assertions.assertEquals(task, manager.getTask(1), "Объект Task не найден в менеджере");
        Assertions.assertEquals(subTask, manager.getSubtask(2), "Объект Subtask не найден в менеджере");
        Assertions.assertEquals(epic, manager.getEpic(3), "Объект Epic не найден в менеджере");
    }

    @Test
    public void equalsTaskAfterAddInManager() {
        Task task1 = manager.getTask(1);
        Assertions.assertEquals(task.getId(),
                task1.getId(), "Поле id объекта Task изменено в менеджере");
        Assertions.assertEquals(task.getName(),
                task1.getName(), "Поле name объекта Task изменено в менеджере");
        Assertions.assertEquals(task.getDescription(),
                task1.getDescription(), "Поле description объекта Task изменено в менеджере");
        Assertions.assertEquals(task.getTaskProgressStatus(),
                task1.getTaskProgressStatus(), "Поле taskstatus объекта Task изменено в менеджере");

    }

    @Test
    public void deleteAllTasks() {
        Task task1 = new Task("new task2", "desc2", NEW);
        Epic epic1 = new Epic("new epic", "desc", NEW);
        SubTask subTask1 = new SubTask("new subtask1", "desc", NEW, epic1);
        manager.addNewEpic(epic1);
        manager.addNewTask(task1);
        manager.addNewSubtask(subTask1);
        Assertions.assertEquals(2, manager.getTasks().size());
        Assertions.assertEquals(2, manager.getSubtasks().size());
        Assertions.assertEquals(2, manager.getEpics().size());
        manager.deleteAllTasks();
        manager.deleteAllSubtasks();
        manager.deleteAllEpics();
        Assertions.assertEquals(0,
                manager.getTasks().size(), "Объекты Task не удалены из мапы менеджера");
        Assertions.assertEquals(0,
                manager.getSubtasks().size(), "Объекты Subtask не удалены из мапы менеджера");
        Assertions.assertEquals(0,
                manager.getEpics().size(), "Объекты Epic не удалены из мапы менеджера");

    }

    @Test
    public void deleteTasksFromId() {
        Assertions.assertEquals(1, manager.getTasks().size());
        Assertions.assertEquals(1, manager.getSubtasks().size());
        Assertions.assertEquals(1, manager.getEpics().size());
        manager.deleteEpicFromId(3);
        manager.deleteTaskFromId(1);
        manager.deleteSubtaskFromId(2);
        Assertions.assertEquals(0, manager.getEpics().size(), "Объект Epic не удален по id");
        Assertions.assertEquals(0, manager.getSubtasks().size(), "Объект Subtask не удален по id");
        Assertions.assertEquals(0, manager.getTasks().size(), "Объект Task не удален по id");
    }

    @Test
    public void checkEpicStatusAfterUpdate() {
        SubTask subTask1 = new SubTask("new Subtask1", "new desc", NEW, epic);
        manager.addNewSubtask(subTask1);
        SubTask subTask2 = new SubTask("new Subtask3", "new desc",IN_PROGRESS, epic);
        manager.updateSubtask(subTask2, 2);
        Assertions.assertEquals(IN_PROGRESS,
                epic.getTaskProgressStatus(), "Статус Epic после обновления задачи установлен некорректно");
    }

    @Test
    public void getHistoryByHistoryManager() {
        manager.getTask(1);
        manager.getSubtask(2);
        manager.getEpic(3);
        Assertions.assertEquals(3,
                manager.getHistory().size(), "Некорректное количество историй");
    }

    @Test
    public void notMore10HistoriesByHistoryManager() {
        manager.getTask(1);
        manager.getSubtask(2);
        manager.getEpic(3);
        manager.getTask(1);
        manager.getSubtask(2);
        manager.getEpic(3);
        manager.getTask(1);
        manager.getSubtask(2);
        manager.getEpic(3);
        manager.getTask(1);
        manager.getSubtask(2);
        manager.getEpic(3);
        Assertions.assertEquals(10,
                manager.getHistory().size(), "Количество историй не должно превышать 10");
    }
}