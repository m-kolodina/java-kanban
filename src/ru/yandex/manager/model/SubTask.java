package ru.yandex.manager.model;

public class SubTask extends Task {

    private final Epic epic;

    public SubTask(String name, String description, TaskProgressStatus taskProgressStatus, Epic epic) {
        super(name, description, taskProgressStatus);
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    @Override
    public String toString() {
        return "model.SubTask: " + getName() + " Description: " + getDescription()
                + " Status: " + getTaskProgressStatus();
    }
}
