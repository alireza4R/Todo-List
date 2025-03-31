package todo.service;

import db.Database;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import static todo.entity.Step.Status.NotStarted;

public class StepService {
    public static void saveStep(int taskRef, String title) throws InvalidEntityException, CloneNotSupportedException {
        Step temp = new Step();
        temp.taskRef = taskRef;
        temp.title = title;
        temp.status = NotStarted;
        Database.add(temp);
    }
}