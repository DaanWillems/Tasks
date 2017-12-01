package nl.daan.tasks.model;

import java.util.ArrayList;

public interface ITaskRepository {
    public void insert(Task task);

    public void update(Task task);

    public void delete(Task task);

    public ArrayList<Task> getSolved();

    public ArrayList<Task> getAll();

    public Task getById(int id);
}
