package nl.daan.tasks.controller.command;

import nl.daan.tasks.model.ITaskRepository;
import nl.daan.tasks.model.Task;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand extends Command {
    @Override
    public boolean run(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args, ITaskRepository taskRepository) {
        Player p = (Player) commandSender;
        if(args.length != 2) {
            return false;
        }
        if(!args[1].chars().allMatch( Character::isDigit )) {
            return false;
        }
        int id = Integer.parseInt(args[1]);
        Task task = taskRepository.getById(id);
        p.sendMessage(ChatColor.BLUE+"Teleporting to "+task.title);
        p.teleport(task.location);
        return true;
    }
}
