package nl.daan.tasks.controller.command;

import nl.daan.tasks.model.ITaskRepository;
import nl.daan.tasks.model.Task;
import nl.daan.tasks.util.MessageUtil;
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
            Task task = taskRepository.getNext();
            if(task == null) {
                MessageUtil.SendMessage(p,"There are no tasks available");
                return true;
            }
            task.assignedPlayer = p;
            taskRepository.update(task);
            MessageUtil.SendMessage(p,"You have been assigned to "+task.title);
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
            MessageUtil.SendMessage(p,"You have been assigned to "+task.title);
            return true;
        }
        else if(args.length == 3) {
            int id = Integer.parseInt(args[1]);
            Player target = Bukkit.getServer().getPlayer(args[2]);
            Task task = taskRepository.getById(id);
            task.assignedPlayer = target;
            taskRepository.update(task);
            MessageUtil.SendMessage(p,args[2]+" has been assigned to "+task.title);
            if(target != null) {
                MessageUtil.SendMessage(target, "You have been assigned to "+task.title);
            }
            return true;
        }
        return false;
    }
}
