package todo.Serializer;

import db.Entity;
import db.Serializer;
import todo.entity.Step;

import java.util.Date;

public class StepSerializer implements Serializer {

    @Override
    public String serialize(Entity e) {
        Step step = (Step) e;
        return step.id + "|" + step.taskRef + "|" + step.title;
    }

    @Override
    public Entity deserialize(String s) {
        try {
            String[] parts = s.split("\\|", -1);
            int id = Integer.parseInt(parts[0]);
            int taskRef = Integer.parseInt(parts[1]);
            String title = parts[2];

            Step step = new Step(taskRef, title, Step.Status.NotStarted);
            step.id = id;
            return step;
        } catch (Exception ex) {
            System.out.println("deserialize step failed: " + ex.getMessage());
            return null;
        }
}
}
