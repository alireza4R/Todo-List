import java.util.ArrayList;
import db.*;
import db.exception.InvalidEntityException;
import todo.service.StepService;
import todo.validator.StepValidator;
import todo.validator.TaskValidator;

import static todo.entity.Step.STEP_ENTITY_CODE;
import static todo.entity.Task.TASK_ENTITY_CODE;


public class Main {
    public static void main(String[] args) throws InvalidEntityException, CloneNotSupportedException {
        Database.registerValidator(STEP_ENTITY_CODE ,  new StepValidator());
        Database.registerValidator(TASK_ENTITY_CODE ,  new TaskValidator());
    }
}