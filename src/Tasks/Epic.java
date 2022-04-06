package Tasks;

import Manager.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class Epic extends Tasks.Task {
    private ArrayList<Tasks.SubTask> subTaskList;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration duration;

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
        if (getStartTime() == null || getEndTime() == null || getDuration() == null) {
            return getiD() + "," + TaskType.EPIC + "," + getTaskName() + "," + getTaskStatus() + "," +
                    getTaskDescription();
        } else return getiD() + "," + TaskType.EPIC + "," + getTaskName() + "," + getTaskStatus() + "," +
                getTaskDescription() + "," + getStartTime().toString() + "," + getEndTime().toString() + "," + getDuration().toString();
    }


    public Duration getDuration() {
        return duration;
    }

    @Override
    public void setDuration(Duration durat) {
        if (!subTaskList.isEmpty()) {
            for (SubTask s : subTaskList) {
                if (!(durat == null)) {
                    duration = durat.plus(s.getDuration());
                } else duration = s.getDuration();
            }
        }
    }

    public void setStartTime(LocalDateTime start) {
        if (!subTaskList.isEmpty()) {
            for (SubTask s : subTaskList) {
                if (!(startTime == null)) {
                    if (s.getStartTime().isBefore(start)) {
                        startTime = s.getStartTime();
                    }
                } else startTime = s.getStartTime();
            }
        }
    }
   // @Override
    public LocalDateTime getStartTime() {
        return startTime;
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

    //@Override
    public LocalDateTime getEndTime() {
        return endTime;
    }
}
