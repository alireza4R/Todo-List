package db;

import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import java.util.Date;

import java.util.HashMap;
import java.util.ArrayList;

public class Database {
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static int num = 1;
    private static HashMap<Integer, Validator> validators = new HashMap<>();

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

    public static Boolean check (int id)  {
        for (Entity temp : entities){
            if(temp.id == id){
                return true;
            }
        }
        return false;
    }
}
