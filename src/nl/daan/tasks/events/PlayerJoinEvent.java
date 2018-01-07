package nl.daan.tasks.events;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import nl.daan.tasks.model.ITaskRepository;
import nl.daan.tasks.model.Task;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.ArrayList;

/**
 * Created by solaw on 7-12-2017.
 */
public class PlayerJoinEvent implements Listener {

    private ITaskRepository taskRepository;

    public PlayerJoinEvent(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @EventHandler
    public void PlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        Player p = event.getPlayer();
        ArrayList<Task> tasks = taskRepository.getAssignedTasks(p);
        TextComponent header = new TextComponent( "---------Your Tasks---------\n" );
        header.setColor(ChatColor.GOLD);
        header.setBold(true);
        TextComponent content =  new TextComponent("");
        for(Task t : tasks) {
            content.addExtra(t.print(false));
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
    }
}
