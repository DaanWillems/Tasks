package nl.daan.tasks.model;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public interface ITaskRepository {
    public void insert(Task task);

    public void update(Task task);

    public void delete(Task task);

    public ArrayList<Task> getUnsolved();

    public ArrayList<Task> getAll();

    public Task getById(int id);

    ArrayList<Task> getAssignedTasks(Player p);

    ArrayList<Task> getUnassigned();
}
