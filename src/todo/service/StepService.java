package todo.service;

import db.Database;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import static todo.entity.Step.Status.NotStarted;
import java.util.Scanner;

public class StepService {
    public static void saveStep(int taskRef, String title) throws InvalidEntityException, CloneNotSupportedException {
        Step temp = new Step(taskRef , title , NotStarted);
        Database.add(temp);
    }

    public static void addStep(){
        Scanner scn = new Scanner(System.in);

        System.out.println("Enter id of task:");
        int taskId = scn.nextInt();

        System.out.println("Enter title of task:");
        String title = scn.nextLine();

        Step temp = new Step(taskId , title , NotStarted);

        boolean flag = false;
        try {
            Database.add(temp);

            flag = true;
        }
        catch(Exception e){
            System.out.println("Cannot save step." + "\n Error:" + e.getMessage());
        }

        if(flag){
            System.out.println( "Step saved successfully." + "\n" + "ID: " + taskId + "Creation Date: " + );
        }
    }
}