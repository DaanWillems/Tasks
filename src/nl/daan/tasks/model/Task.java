package nl.daan.tasks.model;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Task {

    public int id;
    public String title;
    public String description;
    public Player creator;
    public Location location;
    public boolean solved;
    public Player assignedPlayer;

    public Task() {

    }

    public Task(int id, String title, String description, Player creator, Location location, boolean solved, Player assignedPlayer) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creator = creator;
        this.location = location;
        this.solved = solved;
        this.assignedPlayer = assignedPlayer;
    }

    /**
     * Returns a string that contains all relevant task data
     */
    public String print() {
        //Generate equal spacing
        //Also doesnt work for shit
        int spaces = 10-title.length();
        String titleString = title;
        for(int i = 0; i < spaces; i++) {
            titleString += " ";
        }
        return titleString;
    }
}
