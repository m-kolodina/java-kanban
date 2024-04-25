package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.manager.model.Epic;
import ru.yandex.manager.model.SubTask;
import ru.yandex.manager.model.Task;

import static ru.yandex.manager.model.TaskProgressStatus.NEW;

class TasksTest {

    @Test
    public void checkEqualsIdTasks() {
        Task task = new Task("New Task", "New task description", NEW);
        task.setId(0);
        Task task1 = new Task("New Task1", "New task description1", NEW);
        task1.setId(0);
        Assertions.assertEquals(task1, task, "Объекты Task не равны при одинаковых id");
    }

    @Test
    public void checkEqualsIdTaskInheritors() {
        Epic epic = new Epic("new epic", "new epic descripion", NEW);
        epic.setId(1);
        Epic epic1 = new Epic("new epic1", "new epic descripion1", NEW);
        epic1.setId(1);
        SubTask subTask = new SubTask("New Subtask", "New Subtask description", NEW, epic);
        subTask.setId(2);
        SubTask subTask1 = new SubTask("New Subtask1", "New Subtask description1", NEW, epic1);
        subTask1.setId(2);

        Assertions.assertEquals(epic1, epic, "Объекты Epic не равны при одинаковых id");
        Assertions.assertEquals(subTask, subTask1, "Объекты Subtask не равны при одинаковых id");
    }

    @Test
    public void getSubtasksEpic() {
        Epic epic = new Epic("new epic", "new epic descripion", NEW);
        SubTask subTask = new SubTask("New Subtask", "New Subtask description", NEW, epic);
        Assertions.assertEquals(epic, subTask.getEpic(), "У Subtask другой объект Epic");
    }
}
