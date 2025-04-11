package todo.service;

import db.Database;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

import static todo.entity.Step.Status.NotStarted;
import static todo.entity.Task.Status.InProgress;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class StepService {
    public static void saveStep(int taskRef, String title) throws InvalidEntityException, CloneNotSupportedException {
        Step temp = new Step(taskRef, title, NotStarted);
        Database.add(temp);
    }

    public static void addStep() {
        Scanner scn = new Scanner(System.in);

        System.out.println("Enter id of task:");
        int taskId;
        try {
           taskId = scn.nextInt();
        }
        catch (Exception e){
            System.out.println("Wrong id");
            return;
        }
        System.out.println("Enter title of Step:");
        Scanner scanner = new Scanner(System.in);
        String title = scanner.nextLine();

        Step temp = new Step(taskId, title, NotStarted);

        boolean flag = false;
        try {
            Database.add(temp);

            flag = true;
        } catch (Exception e) {
            System.out.println("Cannot save step." + "\n Error:" + e.getMessage());
            return;
        }

        if (flag) {
            System.out.println("Step saved successfully." + "\n" + "ID: " + temp.id + "\n" + "Creation Date: " + temp.getCreationDate());
        }
    }

    public static void updateStep() {
        Scanner scn = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the id of Step you want to change");
            String id = scn.nextLine();
            int idNum = Integer.parseInt(id);

            System.out.println("Enter the name of field that you want to change" + "\ntitle \ndescription \nstatus \nduedate\n");
            String entry = scn.nextLine();
            System.out.println("Enter new value\n");
            if(entry.equals("status")){
                System.out.println("You can choose between:\nCompleted\nInProgress\nNotStarted ");
            }
            String newValue = scn.nextLine();
            Step temp = null;
            String temp2 = null;
            try {
                temp = (Step) Database.get(idNum);
            } catch (Exception e) {
                System.out.println(e.getMessage() + "what??");
                return;
            }

            boolean flag = false;
            switch (entry) {
                case "title":
                    try {
                        temp2 = temp.title;
                        temp.title = newValue;
                        Database.update(temp);
                    } catch (Exception e) {
                        System.out.println("Cannot update step with ID=" + idNum + "\n" + e.getMessage());
                        return;
                    }
                    break;

                case "status":
                    try {
                        System.out.println();
                        temp2 = temp.status.toString();
                        temp.status = Step.Status.valueOf(newValue);
                        Database.update(temp);
                        Task refTask= (Task) Database.get(idNum);
                        refTask.status = InProgress;
                        Database.checkIfCompleted(idNum);

                    } catch (Exception e) {
                        System.out.println("Cannot update step with ID=" + idNum + "\n" + e.getMessage());
                        return;
                    }
                    break;

                default:
                    System.out.println("you entered wrong field. try again.");
                    flag = true;
            }

            if (!flag) {
                System.out.println("Successfully updated the step.\n" +
                        "Field: " + entry + "\n" +
                        "Old Value: " + temp2 + "\n" +
                        "New Value: " + newValue + "\n" +
                        "Modification Date: " + new Date());
                break;
            }
        }
    }


}