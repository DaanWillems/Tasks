package nl.daan.tasks.controller.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import nl.daan.tasks.model.ITaskRepository;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by solaw on 25-11-2017.
 */
public class HelpCommand extends Command{
    @Override
    public boolean run(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args, ITaskRepository taskRepository) {
        Player p = (Player) commandSender;
        TextComponent header = new TextComponent( "---------Commands---------\n" );
        header.setColor(ChatColor.GOLD);
        header.setBold(true);

        TextComponent content = new TextComponent( "" );

        TextComponent helpCommand = new TextComponent( "/help - Shows this window\n" );
        helpCommand.setColor(ChatColor.BLUE);
        content.addExtra(helpCommand);

        TextComponent createCommand = new TextComponent( "/create {title} {description} - Creates a new task\n" );
        createCommand.setColor(ChatColor.BLUE);
        content.addExtra(createCommand);

        TextComponent listAllCommand = new TextComponent( "/list all - Lists available tasks\n" );
        listAllCommand.setColor(ChatColor.BLUE);
        content.addExtra(listAllCommand);

        TextComponent listCommand = new TextComponent( "/list - Lists your tasks\n" );
        listCommand.setColor(ChatColor.BLUE);
        content.addExtra(listCommand);

        TextComponent claimCommand = new TextComponent( "/claim - Gives you a task to complete\n" );
        claimCommand.setColor(ChatColor.BLUE);
        content.addExtra(claimCommand);

        TextComponent message = new TextComponent( "" );
        message.addExtra(header);
        message.addExtra(content);

        p.spigot().sendMessage(message);
        return true;
    }
}
