package todo.validator;

import db.Database;
import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

public class TaskValidator implements Validator {

    @Override
    public void validate(Entity entity) throws InvalidEntityException{
        if(!(entity instanceof Step)){
            throw new IllegalArgumentException("object must be type of Step class");
        }
        Task temp = (Task)entity;

        if(temp.title == null){
            throw new InvalidEntityException("title is empty");
        }

        if(!(Database.check(temp.id))){
            throw new InvalidEntityException("id doesn't exist");
        }

    }
}
