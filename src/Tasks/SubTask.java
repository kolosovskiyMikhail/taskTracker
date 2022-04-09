package Tasks;

import Other.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;

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

}
