package nl.daan.tasks.controller.command;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import nl.daan.tasks.model.ITaskRepository;
import nl.daan.tasks.model.Task;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SolveCommand extends Command {

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
        p.sendMessage(ChatColor.BLUE+task.title+" marked as solved");
        task.solved = true;
        taskRepository.update(task);
        return true;
    }
}
