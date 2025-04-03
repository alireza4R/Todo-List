package todo.service;

import db.Database;
import db.Entity;
import db.exception.InvalidEntityException;
import todo.entity.*;
import static todo.entity.Task.Status.*;
import java.text.SimpleDateFormat;
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
}


