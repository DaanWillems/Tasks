package nl.daan.tasks.controller.command;

import nl.daan.tasks.model.ITaskRepository;
import nl.daan.tasks.util.MessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by solaw on 25-11-2017.
 */
public class Command  {
    public boolean run(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings, ITaskRepository taskRepository) {
        Player p = (Player) commandSender;
        MessageUtil.SendMessage(p,"command not yet implemented");
        return true;
    }

    public String permissionRequired() {
        return "none";
    }
}
