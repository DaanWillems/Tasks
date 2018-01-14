package nl.daan.tasks.model;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DummyTaskRepository implements ITaskRepository {

    public ArrayList<Task> tasks = new ArrayList<Task>();
    private int id;

    public DummyTaskRepository() {
       // tasks.add(new Task(1, "Task 1", "Task 1 desc", null, null, false, null));
    }

    public void insert(Task task) {
        task.id = id;
        id++;
        tasks.add(task);
    }

    public void update(Task task) {

    }

    public void delete(Task task) {
        tasks.remove(task);
    }

    public ArrayList<Task> getUnsolved(int pageId, int pageSize) {
        return tasks.stream()
                .filter(t->!t.solved)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Task> getAll(int pageId, int pageSize) {
        return tasks;
    }

    public Task getById(int id) {
        return tasks.stream()
                .filter(t->t.id == id)
                .findFirst().orElse(null);
    }

    @Override
    public Task getNext() {
        return null;
    }

    @Override
    public ArrayList<Task> getAssignedTasks(Player p) {
        return null;
    }

    @Override
    public ArrayList<Task> getUnassigned(int pageId, int pageSize) {
        return null;
    }
}
