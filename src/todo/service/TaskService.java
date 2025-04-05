package todo.service;

import db.Database;
import db.Entity;
import db.exception.InvalidEntityException;
import todo.entity.*;
import static todo.entity.Task.Status.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

public class TaskService {
    public static void setAsCompleted(int taskId) throws CloneNotSupportedException, InvalidEntityException {
        Task temp = (Task)Database.get(taskId);
        temp.status = Completed;
        Database.update(temp);
    }

    public void addTask (){
        Scanner scn = new Scanner(System.in);

        System.out.println("Enter title of task");
        String title = scn.nextLine();

        System.out.println("Enter description of task");
        String description = scn.nextLine();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Enter due date with format: yyyy-mm-dd");
        String dueDate = scn.nextLine();
        Date date = null;

        try {
           date = formatter.parse(dueDate);
        } catch (Exception e) {
            System.out.println("Wrong format");
        }

        Task temp = new Task(title , description , date , NotStarted);

        boolean flag = false;
        try {
            Database.add(temp);

            flag = true;
        }

        catch(Exception e){
            System.out.println("Cannot save task." + "\n Error:" + e.getMessage());
        }

        if(flag){
            System.out.println( "task saved successfully." + "\n" + "ID: " + temp.id  );
        }
    }

    public void updateTask (){
        Scanner scn = new Scanner(System.in);

        while(true){
            System.out.println("Enter the id of task you want to change" );
            String id = scn.nextLine();
            int idNum = Integer.parseInt(id);

            System.out.println("Enter the name of field that you want to change" + "\ntitle \ndescription\n status \n duedate \n");
            String entry =  scn.nextLine();
            System.out.println("Enter new value");
            String newValue = scn.nextLine();
            Task temp = null;
            String temp2 = null;
            try {
                temp = (Task) Database.get(idNum);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }

            boolean flag = false;
            switch (entry){
                case "title" :
                    try {
                         temp2 = temp.title;
                        temp.title = newValue;
                        Database.update(temp);
                    }
                    catch (Exception e){
                    System.out.println("Cannot update task with ID=" + idNum + "\n" + e.getMessage());
                    }
                    break;
                case "descreption" :
                    try {
                         temp2 = temp.description;
                        temp.title = newValue;
                        Database.update(temp);
                    }
                    catch (Exception e){
                        System.out.println("Cannot update task with ID=" + idNum + "\n" + e.getMessage());
                    }
                    break;
                case "status" :
                    try {
                         temp2 = temp.status.toString();
                        temp.status = Task.Status.valueOf(newValue);
                        Database.update(temp);
                        Database.modifyComponents(idNum);
                    }
                    catch (Exception e){
                        System.out.println("Cannot update task with ID=" + idNum + "\n" + e.getMessage());
                    }
                    break;
                case "duedate" :
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;

                    try {
                         temp2 = temp.dueDate.toString();
                        date = formatter.parse(newValue);
                        temp.dueDate = date;
                        Database.update(temp);
                    } catch (Exception e) {
                        System.out.println("Cannot update task with ID=" + idNum + "\n" + e.getMessage());
                    }
                    break;
                default:
                    System.out.println("you entered wrong field. try again.");
                   flag = true;
            }

            if(!flag){
                System.out.println("Successfully updated the task.\n" +
                        "Field: " + entry + "\n" +
                        "Old Value: " + temp2 + "\n" +
                        "New Value: " + newValue + "\n" +
                        "Modification Date: " + new Date());
                break;
            }
        }
    }

    public static void getTask (){
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter the id of task you want to change" );
        String taskId = scn.nextLine();
        int idNum = Integer.parseInt(taskId);
        Task temp = null;
        try{
            temp = (Task) Database.get(idNum);
            System.out.println("ID: " + taskId + "\nTitle: " + temp.title + "\nDue Date: " + temp.dueDate + "\nStatus: " + temp.status + "Steps:\n");
            Database.printSteps(idNum);
        }
        catch (Exception e){
            System.out.println("Cannot find task with ID=7.");
        }
    }

    public static void printAllTasks(){
        ArrayList<Entity> temp = Database.getAll(1);

        for (int i = 0 ; i<temp.size() ; i++){
            for (int j=i+1 ; j< temp.size() ; j++)
                if(((Task)temp.get(i)).dueDate.compareTo(((Task)temp.get(j)).dueDate) > 0){
                    Entity temp2 = temp.get(i);
                    temp.set(i,temp.get(j));
                    temp.set(j,temp2);

                }
        }

        for (Entity newTask:temp){
            System.out.println("(ID: " + ((Task)newTask).id + "\nTitle: " + ((Task)newTask).title + "\nDue date: " + ((Task) newTask).dueDate + "Status: " + ((Task) newTask).status + "\n") ;
            Database.printSteps(newTask.id);
        }
    }

    public static void printAllIncomplete(){
        ArrayList<Entity> temp = Database.getAll(1);
        for (Entity temp2 : temp){
            if(((Task)temp2).status != Completed){
                System.out.println("(ID: " + ((Task)temp2).id + "\nTitle: " + ((Task)temp2).title + "\nDue date: " + ((Task) temp2).dueDate + "Status: " + ((Task) temp2).status + "\n");
                Database.printSteps(temp2.id);
            }
        }

    }
}


