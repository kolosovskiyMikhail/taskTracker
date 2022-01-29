package Tasks;

import Tasks.Task;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<SubEpic> subEpicList;

    public Epic(String taskName, String taskDescription, String taskStatus) {
        super(taskName, taskDescription, taskStatus);
    }

    public ArrayList<SubEpic> getSubEpicList() {
        return subEpicList;
    }

    public void setSubEpicList(ArrayList<SubEpic> subEpicList) {
        this.subEpicList = subEpicList;
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

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Tasks.Epic{" +
                "taskName='" + getTaskName() + '\'' +
                ", taskDescription='" + getTaskDescription() + '\'' +
                ", taskStatus='" + getTaskStatus() + '\'' +
                '}';
    }
}
