package nl.daan.tasks.controller.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import nl.daan.tasks.model.ITaskRepository;
import nl.daan.tasks.model.Task;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.StringJoiner;

public class ListCommand extends Command {

    @Override
    public boolean run(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings, ITaskRepository taskRepository) {
        Player p = (Player) commandSender;

        ArrayList<Task> tasks = taskRepository.getSolved();

        TextComponent header =  new TextComponent( "---------Tasks---------\n" );
        header.setColor(ChatColor.GOLD);
        header.setBold(true);
        TextComponent content =  new TextComponent("");
        for(Task t : tasks) {
            TextComponent element =  new TextComponent("- " +t.print() );
            element.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(t.description+"\n"+t.creator.getDisplayName()).create() ) );
            element.setColor(ChatColor.AQUA);
            element.setBold(true);

            TextComponent teleport = new TextComponent("  Teleport " );
            teleport.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Teleport to this task").create() ) );
            teleport.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/task teleport "+t.id) );
            teleport.setColor(ChatColor.BLUE);
            teleport.setBold(true);

            TextComponent solve = new TextComponent(" Solve " );
            solve.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Mark this task as solved").create() ) );
            solve.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/task solve "+t.id) );
            solve.setColor(ChatColor.GREEN);
            solve.setBold(true);

            TextComponent delete = new TextComponent(" Delete \n" );
            delete.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Delete this task").create() ) );
            delete.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/task delete "+t.id) );
            delete.setColor(ChatColor.RED);
            delete.setBold(true);

            element.addExtra(teleport);
            element.addExtra(solve);
            element.addExtra(delete);
            content.addExtra(element);
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
