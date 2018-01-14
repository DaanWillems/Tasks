package nl.daan.tasks.model;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.apache.commons.lang.WordUtils;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class Task {

    public int id;
    public String title;
    public String description;
    public Player creator;
    public String creatorUUID;
    public Location location;
    public boolean solved;
    public Player assignedPlayer;
    public String assignedUUID;
    public LocalDate deadline;

    public Task() {

    }

    public Task(int id, String title, String description, Player creator, Location location, boolean solved, Player assignedPlayer, LocalDate deadline) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creator = creator;
        this.location = location;
        this.solved = solved;
        this.assignedPlayer = assignedPlayer;
        this.deadline = deadline;
    }

    /**
     * Returns a textcomponent that contains all relevant task data
     */
    public TextComponent print(boolean listingAll) {

        int spaces = 10;
        spaces -= title.length();

        String spacesString = "";

        for(int i = 0; i < spaces; i++) {
            spacesString += " ";
        }

        TextComponent element =  new TextComponent("- " +title+spacesString );
        String creatorName = "";
        if(creator == null) {
            creatorName = Bukkit.getOfflinePlayer(UUID.fromString(creatorUUID)).getName();
        } else {
            creatorName = creator.getDisplayName();
        }
        String assignedName = "";

        if(assignedPlayer == null) {
            if(assignedUUID != null) {
                assignedName = "\n"+Bukkit.getOfflinePlayer(UUID.fromString(creatorUUID)).getName();
            }
        } else {
            assignedName = "\n"+assignedPlayer.getPlayerListName();
        }

        String deadlineString = "None";
        if(deadline != null) {
            deadlineString = deadline.toString();
        }

        element.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Id: "+id+"\n"
                +"Description: "+WordUtils.wrap(description, 30, "\n", true)+"\n"
                +"Creator: "+creatorName+"\n"
                +"Deadline: "+deadlineString).create()));
        element.setColor(ChatColor.AQUA);
        element.setBold(true);

        TextComponent assign = null;
        if(listingAll) {
            assign = new TextComponent("  Claim " );
            assign.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Assign this task to yourself").create() ) );
            assign.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/task assign "+id) );
            assign.setColor(ChatColor.DARK_AQUA);
            assign.setBold(true);
        } else {
            assign = new TextComponent(" Unclaim " );
            assign.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Remove this task from your list").create() ) );
            assign.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/task unassign "+id) );
            assign.setColor(ChatColor.GRAY);
        }

        TextComponent teleport = new TextComponent(" Teleport " );
        teleport.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Teleport to this task").create() ) );
        teleport.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/task teleport "+id) );
        teleport.setColor(ChatColor.BLUE);
        teleport.setBold(true);

        TextComponent solve = null;
        if(listingAll) {
            solve = new TextComponent(" Solve " );
            solve.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Not available until assigned").create() ) );
            solve.setColor(ChatColor.GRAY);
            solve.setStrikethrough(true);
            solve.setBold(true);
        } else {
            solve = new TextComponent(" Solve " );
            solve.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Mark this task as solved").create() ) );
            solve.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/task solve "+id) );
            solve.setColor(ChatColor.GREEN);
            solve.setBold(true);
        }
        TextComponent delete = new TextComponent(" Delete \n" );
        delete.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Delete this task").create() ) );
        delete.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/task delete "+id) );
        delete.setColor(ChatColor.RED);
        delete.setBold(true);

        element.addExtra(assign);
        element.addExtra(teleport);
        element.addExtra(solve);
        element.addExtra(delete);

        if(solved) {
            element.setStrikethrough(true);
        }
        return element;
    }
}
