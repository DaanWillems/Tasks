package nl.daan.tasks.controller.command;

import nl.daan.tasks.model.ITaskRepository;
import nl.daan.tasks.model.Task;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CreateCommand extends Command {
    @Override
    public boolean run(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args, ITaskRepository taskRepository) {
        Player p = (Player) commandSender;
        Task task = new Task();

        //We are only expecting 2 arguments, but the initial "create" is includes in this list as well
        if(args.length < 3) {
            return false;
        }

        task.title = args[1];
        String desc = "";
        for(int i = 2; i < args.length; i++) {
            desc += args[i]+" ";
        }

        task.description = desc;
        task.creator = p;
        task.creatorUUID = p.getUniqueId().toString();
        task.location = p.getLocation();

        taskRepository.insert(task);
        p.sendMessage(ChatColor.BLUE+"Task created. ("+task.id+")");

        return true;
    }
}
