package todo.Serializer;

import db.Entity;
import db.Serializer;
import todo.entity.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskSerializer implements Serializer {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    public String serialize(Entity e) {
        Task task = (Task) e;
        return task.id + "|" + task.title + "|" + task.description + "|" +
                format.format(task.dueDate) + "|" +
                format.format(task.getCreationDate()) + "|" +
                format.format(task.getLastModificationDate());
    }

    @Override
    public Entity deserialize(String s) {
        try {
            String[] parts = s.split("\\|", -1);
            int id = Integer.parseInt(parts[0]);
            String title = parts[1];
            String description = parts[2];
            Date dueDate = format.parse(parts[3]);
            Date creationDate = format.parse(parts[4]);
            Date modificationDate = format.parse(parts[5]);

            Task task = new Task(title, description, dueDate, Task.Status.NotStarted);
            task.id = id;
            task.setCreationDate(creationDate);
            task.setLastModificationDate(modificationDate);
            return task;
        } catch (Exception ex) {
            System.out.println("deserialize task failed: " + ex.getMessage());
            return null;
        }
    }
}


