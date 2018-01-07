package nl.daan.tasks.controller.command;

import nl.daan.tasks.model.ITaskRepository;
import nl.daan.tasks.model.Task;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AssignCommand extends nl.daan.tasks.controller.command.Command {
    @Override
    public boolean run(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args, ITaskRepository taskRepository) {
        Player p = (Player) commandSender;
        if(args.length > 3) {
            return false;
        }
        if(args.length == 1) {
            Task task = taskRepository.getUnassigned().get(0);
            task.assignedPlayer = p;
            taskRepository.update(task);
            p.sendMessage(ChatColor.BLUE+"You have been assigned to "+task.title);
            return true;
        }
        else if(args.length == 2) {
            if(!args[1].chars().allMatch( Character::isDigit )) {
                return false;
            }

            int id = Integer.parseInt(args[1]);
            Task task = taskRepository.getById(id);
            task.assignedPlayer = p;
            taskRepository.update(task);
            p.sendMessage(ChatColor.BLUE+"You have been assigned to "+task.title);
            return true;
        }
        else if(args.length == 3) {
            int id = Integer.parseInt(args[1]);
            Player target = Bukkit.getServer().getPlayer(args[2]);
            Task task = taskRepository.getById(id);
            task.assignedPlayer = target;
            taskRepository.update(task);
            p.sendMessage(ChatColor.BLUE+args[2]+" has been assigned to "+task.title);
            target.sendMessage(ChatColor.BLUE+"You have been assigned to "+task.title);
            return true;
        }
        return false;
    }
}
