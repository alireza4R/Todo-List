package todo.service;
import db.Database;
import todo.entity.Task;

import java.util.Scanner;


public class GeneralService {

    public void delete(){
        Scanner scn = new Scanner(System.in);

        System.out.println("Enter id of Entity");
        String id = scn.nextLine();

        int idNum = Integer.parseInt(id);
        boolean flag = false;
        boolean flag2 = false;

        try{
            if(Database.get(idNum) instanceof Task){
                flag2 = true;
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        try {

            Database.delete(idNum);

            flag = true;
        }

        catch(Exception e){
            System.out.println("Cannot delete Entity with ID=" + id + ".\n Error:" + e.getMessage());
        }

        if(flag){
            if(flag2)
            Database.deleteComponents(idNum);

            System.out.println("Entity with ID=" + id + " successfully deleted.");
        }
    }


}
