import java.util.ArrayList;
import db.*;
import db.exception.InvalidEntityException;
import todo.Serializer.StepSerializer;
import todo.Serializer.TaskSerializer;
import todo.service.GeneralService;
import todo.service.StepService;
import todo.service.TaskService;
import todo.validator.StepValidator;
import todo.validator.TaskValidator;
import java.util.Scanner;
import static todo.entity.Step.STEP_ENTITY_CODE;
import static todo.entity.Task.TASK_ENTITY_CODE;


public class Main {
    public static void main(String[] args) throws InvalidEntityException, CloneNotSupportedException {
        Database.registerSerializer(STEP_ENTITY_CODE , new StepSerializer());
        Database.registerSerializer(TASK_ENTITY_CODE , new TaskSerializer());
        Database.registerValidator(STEP_ENTITY_CODE ,  new StepValidator());
        Database.registerValidator(TASK_ENTITY_CODE ,  new TaskValidator());

        Database.load();

        String entry;
        Scanner scn = new Scanner(System.in);
        boolean flag = true;

        while(flag){
            System.out.println("Commands:\n1: add task\n2: add step\n3: delete\n4: update task\n5: update step\n6: get task-by-id\n7: get all-tasks\n8: get incomplete-tasks\n9: Save\n0: exit\n enter number: ");
            entry = scn.nextLine();

            switch (entry){
                case "1":
                    TaskService.addTask();
                    break;
                case "2":
                    StepService.addStep();
                    break;
                case "3":
                    GeneralService.delete();
                    break;
                case "4":
                    TaskService.updateTask();
                    break;
                case "5":
                    StepService.updateStep();
                    break;
                case "6":
                    TaskService.getTask();
                    break;
                case "7":
                    TaskService.printAllTasks();
                    break;
                case "8":
                    TaskService.printAllIncomplete();
                    break;
                case "0":
                    flag =false;
                    break;
                case "9":
                    Database.save();
                    break;
                default:
                    System.out.println("Invalid entry.try again.\n");

            }
            System.out.println("****************************************************************");
        }


    }
}