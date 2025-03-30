package todo.entity;

import db.Entity;
import db.Trackable;

import java.util.Date;

public class Task extends Entity implements Trackable {
    public static final int TASK_ENTITY_CODE = 1;
    public String title;
    public String description;
    public Date dueDate;
    public Status status;


    public enum Status{
        NotStarted , InProgress , Completed;
    }

    @Override
    public int getEntityCode() {
        return TASK_ENTITY_CODE;
    }

    @Override
    public void setCreationDate(Date date) {
        this.creationDate = new Date(date.getTime());
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = new Date(date.getTime());
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }

}
