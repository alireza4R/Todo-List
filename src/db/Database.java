package db;

import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;

import static todo.entity.Step.Status.Completed;

public class Database {
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static int num = 1;
    private static HashMap<Integer, Validator> validators = new HashMap<>();
    private static HashMap<Integer, Serializer> serializers = new HashMap<>();

    public static void add(Entity e) throws CloneNotSupportedException, InvalidEntityException {
        e.id = num;
        num++;

        Validator validator = validators.get(e.getEntityCode());
        if(validator != null)
        validator.validate(e);

        if(e instanceof Trackable){
            Trackable trackableEntity = (Trackable) e;
            Date now = new Date();
            trackableEntity.setCreationDate(now);
            trackableEntity.setLastModificationDate(now);
        }

        entities.add(e.clone());
    }

    public static Entity get (int id) throws CloneNotSupportedException {
        for (Entity temp : entities){
            if(temp.id == id){
                return temp.clone();
            }
        }
        throw new EntityNotFoundException();
    }

    public static void delete(int id){
        for (int i = 0 ; i < entities.size() ; i++){
            if(entities.get(i).id == id){
                entities.remove(i);
                return;
            }
        }
        throw new EntityNotFoundException();
    }

    public static void update(Entity e) throws CloneNotSupportedException, InvalidEntityException {

        Validator validator = validators.get(e.getEntityCode());
        if (validator != null)
        validator.validate(e);

        if(e instanceof Trackable){
            Trackable trackableEntity = (Trackable) e;
            Date now = new Date();
            trackableEntity.setLastModificationDate(now);
        }

        for (int i = 0 ; i < entities.size() ; i++){
            if(entities.get(i).id == e.id){
                entities.set(i , e.clone());
                return;
            }
            throw new EntityNotFoundException();
        }
    }

    public static void registerValidator(int entityCode, Validator validator) {

        if(validators.containsKey(entityCode)){
            throw new IllegalArgumentException("a validator for this code already exist");
        }

        validators.put(entityCode, validator);
    }

    public static void registerSerializer(int entityCode, Serializer serializer) {
        if(serializers.containsKey(entityCode)){
            throw new IllegalArgumentException("a serializer for this code already exist");
        }

        serializers.put(entityCode, serializer);
    }

    public static Boolean check (int id)  {
        for (Entity temp : entities){
            if(temp.id == id){
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Entity> getAll(int entityCode) {
        ArrayList<Entity> temp = new ArrayList<>();
        for (Entity temp2 : entities){
            if(temp2.getEntityCode() == entityCode){
                temp.add(temp2);
            }
        }
        return temp;
    }

    public static void deleteComponents (int id){
        for (int i = 0 ; i < entities.size() ; i++){
            if(entities.get(i) instanceof Step){
                if (((Step) entities.get(i)).taskRef == id){
                    entities.remove(i);
                }
            }
        }
    }

    public static void modifyComponents(int id){
        for (int i = 0 ; i < entities.size() ; i++){
            if(entities.get(i) instanceof Step){
                if (((Step) entities.get(i)).taskRef == id){
                    ((Step) entities.get(i)).status = Completed;
                }
            }
        }
    }

    public static void checkIfCompleted (int id){
        for (Entity temp : entities){
            if(temp instanceof Step && ((Step) temp).taskRef == id){
                if(((Step) temp).status != Completed)
                    return;
            }
        }

        Task temp = null;
        try {
           temp = (Task) Database.get(id);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }

        temp.status = Task.Status.Completed;
    }

    public static void printSteps (int id){
        for (Entity temp : entities){
            if(temp instanceof Step && ((Step) temp).taskRef == id)
                System.out.println("+" + ((Step) temp).title + "\nID: " + temp.id + "\nStatus: " + ((Step) temp).status + "\n") ;
        }

    }

    public static void save() {
        try (PrintWriter writer = new PrintWriter("db.txt")) {
            writer.println("NEXT_ID|" + Database.getNextId());
            for (Entity entity : entities) {
                int code = entity.getEntityCode();
                Serializer s = serializers.get(code);
                String serialized = s.serialize(entity);
                writer.println(code + "|" + serialized);
            }
            System.out.println("successfully Saved");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void load() {
        try (Scanner scanner = new Scanner(new File("db.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.startsWith("NEXT_ID|")) {
                    String[] idParts = line.split("\\|");
                    Database.setNextId(Integer.parseInt(idParts[1]));
                    continue;
                }


                String[] parts = line.split("\\|", 2);
                int code = Integer.parseInt(parts[0]);
                String data = parts[1];
                Serializer s = serializers.get(code);
                Entity entity = s.deserialize(data);
                entities.add(entity);
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found ,database is empty.\n");
        }
    }

    public static int getNextId(){
        return num;
    }

    public static void setNextId(int num){
        Database.num = num;
    }
}
