package nl.daan.tasks.model;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public interface ITaskRepository {
    public void insert(Task task);

    public void update(Task task);

    public void delete(Task task);

    public ArrayList<Task> getUnsolved(int pageId, int pageSize);

    public ArrayList<Task> getAll(int pageId, int pageSize);

    public Task getById(int id);

    public Task getNext();

    ArrayList<Task> getAssignedTasks(Player p);

    ArrayList<Task> getUnassigned(int pageId, int pageSize);
}
