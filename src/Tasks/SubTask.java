package Tasks;

import Manager.TaskType;

public class SubTask extends Task {
    private int epicId;

    public SubTask(int epicId, String taskName, String taskDescription, String taskStatus) {
        super(taskName, taskDescription, taskStatus);
        this.epicId = epicId;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        /*return "Tasks.SubEpic{" +
                "taskName='" + getTaskName() + '\'' +
                ", taskDescription='" + getTaskDescription() + '\'' +
                ", taskStatus='" + getTaskStatus() + '\'' +
                '}';*/
        return getiD() + "," + TaskType.SUBTASK + "," + getTaskName() + "," + getTaskStatus() + "," + getTaskDescription() + "," + epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public String getTaskName() {
        return super.getTaskName();
    }

    @Override
    public void setTaskName(String taskName) {
        super.setTaskName(taskName);
    }

    @Override
    public String getTaskDescription() {
        return super.getTaskDescription();
    }

    @Override
    public void setTaskDescription(String taskDescription) {
        super.setTaskDescription(taskDescription);
    }

    @Override
    public String getTaskStatus() {
        return super.getTaskStatus();
    }

    @Override
    public void setTaskStatus(String taskStatus) {
        super.setTaskStatus(taskStatus);
    }

    @Override
    public int getiD() {
        return super.getiD();
    }

    @Override
    public void setiD(int iD) {
        super.setiD(iD);
    }
}
