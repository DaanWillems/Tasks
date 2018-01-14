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
        TextComponent header = new TextComponent( "---------Task Commands---------\n" );
        header.setColor(ChatColor.GOLD);
        header.setBold(true);

        TextComponent content = new TextComponent( "" );

        TextComponent helpCommand = new TextComponent( "/task help - Shows this window\n" );
        helpCommand.setColor(ChatColor.BLUE);
        content.addExtra(helpCommand);

        TextComponent createCommand = new TextComponent( "/task create [arguments] - Creates a new task. \n" +
                "   -t [title] *required\n" +
                "   -i [info/description] *required\n" +
                "   -d [deadline] *required\n" );
        createCommand.setColor(ChatColor.BLUE);
        content.addExtra(createCommand);

        TextComponent updateCommand = new TextComponent( "/task update [arguments] - Updates a task. \n" +
                "   -t [title] \n" +
                "   -i [info/description]\n" +
                "   -d [deadline]\n" +
                "   -id [id] *required\n" );
        updateCommand.setColor(ChatColor.BLUE);
        content.addExtra(updateCommand);

        TextComponent listAllCommand = new TextComponent( "/task list all - Lists available tasks\n" );
        listAllCommand.setColor(ChatColor.BLUE);
        content.addExtra(listAllCommand);

        TextComponent listCommand = new TextComponent( "/task list - Lists your tasks\n" );
        listCommand.setColor(ChatColor.BLUE);
        content.addExtra(listCommand);

        TextComponent claimCommand = new TextComponent( "/task claim - Gives you a task to complete\n" );
        claimCommand.setColor(ChatColor.BLUE);
        content.addExtra(claimCommand);

        TextComponent assighnCommand = new TextComponent( "/task assign [id] [playername] - Assigns task to player\n" );
        assighnCommand.setColor(ChatColor.BLUE);
        content.addExtra(assighnCommand);

        TextComponent message = new TextComponent( "" );
        message.addExtra(header);
        message.addExtra(content);

        p.spigot().sendMessage(message);
        return true;
    }
}
