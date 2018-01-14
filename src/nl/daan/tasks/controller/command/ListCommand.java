package nl.daan.tasks.controller.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import nl.daan.tasks.model.ITaskRepository;
import nl.daan.tasks.model.Task;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class ListCommand extends Command {

    private int pageSize = 10;

    @Override
    public boolean run(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args, ITaskRepository taskRepository) {
        Player p = (Player) commandSender;

        ArrayList<Task> tasks = null;

        TextComponent header = null;

        boolean listingAll = false;

        int pageId = 0;
        String listType = "list";

        //obtain pageid
        switch(args.length) {
            case 2:
                if(!args[1].chars().allMatch( Character::isDigit )) {
                    break;
                }
                pageId = Integer.parseInt(args[1]);
                break;
            case 3:
                if(!args[2].chars().allMatch( Character::isDigit )) {
                    break;
                }
                pageId = Integer.parseInt(args[2]);
                break;
        }

        if(args.length >= 2) {
            if(args[1].chars().allMatch( Character::isDigit )) {
                tasks = taskRepository.getAssignedTasks(p);
                header = new TextComponent( "---------Your Tasks---------\n" );
            }
            else if(args[1].equals("all")) {
                tasks = taskRepository.getUnassigned(pageId, pageSize);
                listType = "list all";
                header = new TextComponent( "---------Available Tasks---------\n" );
                listingAll = true;
            } else if(args[1].equals("all!")) {
                tasks = taskRepository.getAll(pageId, pageSize);
                listType = "list all!";
                header = new TextComponent( "---------All Tasks---------\n" );
                listingAll = true;
            }
        } else {
            tasks = taskRepository.getAssignedTasks(p);
            header = new TextComponent( "---------Your Tasks---------\n" );
        }


        header.setColor(ChatColor.GOLD);
        header.setBold(true);
        TextComponent content =  new TextComponent("");
        for(Task t : tasks) {
            content.addExtra(t.print(listingAll));
        }

        TextComponent footer =  new TextComponent( "" );

        TextComponent next =  new TextComponent( "Next" );
        next.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Go to the next page").create() ) );
        int id = pageId+1;
        next.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/task "+listType+" "+id) );
        next.setColor(ChatColor.GOLD);

        TextComponent divider =  new TextComponent( "   " );

        TextComponent previous =  new TextComponent( "Previous" );
        previous.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Go to the previous page").create() ) );
        id = pageId-1;
        if(pageId-1 >= 0) {
            previous.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/task "+listType+" "+id) );
        } else {
            previous.setStrikethrough(true);
        }
        previous.setColor(ChatColor.GOLD);

        footer.addExtra(previous);
        footer.addExtra(divider);
        footer.addExtra(next);

        TextComponent message =  new TextComponent( "" );
        message.addExtra(header);
        message.addExtra(content);
        message.addExtra(footer);

        p.spigot().sendMessage(message);

        return true;
    }

}
