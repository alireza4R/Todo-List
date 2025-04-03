package todo.validator;

import db.Database;
import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

public class StepValidator implements Validator {

    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Step)) {
            throw new IllegalArgumentException("object must be type of Step class");
        }
        Step temp = (Step) entity;

        if (temp.title == null) {
            throw new InvalidEntityException("title is empty");
        }

        if (!(Database.check(temp.taskRef))) {
            throw new InvalidEntityException("Cannot find task with ID=" + temp.taskRef);
        }
    }
}
