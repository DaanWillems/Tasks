package nl.daan.tasks.controller.command;

import nl.daan.tasks.model.ITaskRepository;
import nl.daan.tasks.model.Task;
import nl.daan.tasks.util.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.util.ArrayList;

public class UpdateCommand extends Command {

    ArrayList<String> flags = new ArrayList<String>() {{
        add("-t");
        add("-i");
        add("-d");
        add("-id");
        add("-l");
    }};

    @Override
    public boolean run(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args, ITaskRepository taskRepository) {
        Player p = (Player) commandSender;
        String title = null;
        String desc = null;
        LocalDate deadline = null;
        boolean updateLoc = false;
        int id = -1;

        for(int i = 1; i < args.length; i++) {
            if(flags.contains(args[i])) {
                if(i+1 >= args.length) {
                    return true;
                }
                String content = "";
                //gather content
                for(int x = i+1; x < args.length; x++) {
                    if(flags.contains(args[x])) {
                        break;
                    }
                    content += args[x]+" ";
                }
                switch(args[i]) {
                    case "-t":
                        title = content;
                        break;
                    case "-i":
                        desc = content;
                        break;
                    case "-id":
                        if(!args[i+1].chars().allMatch( Character::isDigit )) {
                            return false;
                        }
                        id = Integer.parseInt(args[i+1]);
                        break;
                    case "-d":
                        if(!args[i+1].chars().allMatch( Character::isDigit )) {
                            return false;
                        }
                        int days = Integer.parseInt(args[i+1]);
                        deadline = LocalDate.now().plusDays(days);
                        break;
                    case "-l":
                        if(content.contains("true")) {
                            updateLoc = true;
                        }
                        break;
                }
            }
        }


        Task task = taskRepository.getById(id);

        if (title != null) {
            task.title = title;
        }
        if (desc != null) {
            task.description = desc;
        }
        if (updateLoc) {
            task.location = p.getLocation();
        }
        if (deadline != null) {
            task.deadline = deadline;
        }

        taskRepository.update(task);
        MessageUtil.SendMessage(p,"Task updated.");
        return true;
    }
}
