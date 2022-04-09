package Tasks;

import Other.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private String taskName;
    private String taskDescription;
    private String taskStatus;
    private int iD;
    private Duration duration;
    private LocalDateTime startTime;

    public Task(String taskName, String taskDescription, String taskStatus, LocalDateTime startTime, Duration duration) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
        this.iD = iD;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String taskName, String taskDescription, String taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
        this.iD = iD;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return iD == task.iD && Objects.equals(taskName, task.taskName) && Objects.equals(taskDescription,
                task.taskDescription) && Objects.equals(taskStatus, task.taskStatus) && Objects.equals(duration, task.duration)
                && Objects.equals(startTime, task.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, taskDescription, taskStatus, iD, duration, startTime);
    }

    @Override
    public String toString() {
        return iD + "," + TaskType.TASK + "," + taskName + "," + taskStatus + "," + 
                taskDescription + "," + startTime.toString() + "," + getEndTime().toString() + "," + duration.toString();
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }
}
