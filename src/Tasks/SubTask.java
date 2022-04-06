package Tasks;

import Manager.TaskType;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class SubTask extends Task {
    private int epicId;

    public SubTask(int epicId, String taskName, String taskDescription, String taskStatus, LocalDateTime startTime, Duration duration) {
        super(taskName, taskDescription, taskStatus, startTime, duration);
        this.epicId = epicId;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return getiD() + "," + TaskType.SUBTASK + "," + getTaskName() + "," + getTaskStatus() + "," +
                getTaskDescription() + "," + getStartTime().toString() + "," + getEndTime() + "," +
                getDuration().toString() + "," + epicId;
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

    @Override
    public Duration getDuration() {
        return super.getDuration();
    }

    @Override
    public void setDuration(Duration duration) {
        super.setDuration(duration);
    }

    @Override
    public LocalDateTime getStartTime() {
        return super.getStartTime();
    }

    @Override
    public void setStartTime(LocalDateTime startTime) {
        super.setStartTime(startTime);
    }

    //@Override
    public LocalDateTime getEndTime() {
        return getStartTime().plus(getDuration());
    }
}
