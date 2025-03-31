package todo.service;

import db.Database;
import db.Entity;
import db.exception.InvalidEntityException;
import todo.entity.*;
import static todo.entity.Task.Status.*;

public class TaskService {
    public static void setAsCompleted(int taskId) throws CloneNotSupportedException, InvalidEntityException {
        Task temp = (Task)Database.get(taskId);
        temp.status = Completed;
        Database.update(temp);
    }
}


