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

    @Override
    public boolean run(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args, ITaskRepository taskRepository) {
        Player p = (Player) commandSender;

        ArrayList<Task> tasks = null;

        TextComponent header = null;

        boolean listingAll = false;

        if(args.length >= 2) {
            if(args[1].equals("all")) {
                tasks = taskRepository.getUnassigned();
                header = new TextComponent( "---------Available Tasks---------\n" );
                listingAll = true;
            } else if(args[1].equals("all!")) {
                tasks = taskRepository.getAll();
                header = new TextComponent( "---------Available Tasks---------\n" );
                listingAll = true;
            } else {
                return false;
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
        next.setColor(ChatColor.GOLD);

        TextComponent divider =  new TextComponent( "   " );

        TextComponent previous =  new TextComponent( "Previous" );
        previous.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Go to the previous page").create() ) );
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
