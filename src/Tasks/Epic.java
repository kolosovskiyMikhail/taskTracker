package Tasks;

import Other.TaskType;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Tasks.Task {
    private ArrayList<Tasks.SubTask> subTaskList;
    private LocalDateTime endTime;

    public Epic(String taskName, String taskDescription, String taskStatus) {
        super(taskName, taskDescription, taskStatus);
    }

    public ArrayList<SubTask> getSubTaskList() {
        return subTaskList;
    }

    public void setSubTaskList(ArrayList<SubTask> subTaskList) {
        this.subTaskList = subTaskList;
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
        if (getStartTime() == null || getEndTime() == null || getDuration() == null) {
            return getiD() + "," + TaskType.EPIC + "," + getTaskName() + "," + getTaskStatus() + "," +
                    getTaskDescription();
        } else return getiD() + "," + TaskType.EPIC + "," + getTaskName() + "," + getTaskStatus() + "," +
                getTaskDescription() + "," + getStartTime() + "," + getEndTime().toString() + "," + getDuration().toString();
    }

    public void setEndTime(LocalDateTime end) {
        if (!subTaskList.isEmpty()) {
            for (SubTask s : subTaskList) {
                if (!(endTime == null)) {
                    if (s.getEndTime().isAfter(end)) {
                        endTime = s.getEndTime();
                    }
                } else endTime = s.getEndTime();
            }
        }
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
