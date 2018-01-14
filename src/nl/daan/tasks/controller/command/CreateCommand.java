package nl.daan.tasks.controller.command;

import nl.daan.tasks.model.ITaskRepository;
import nl.daan.tasks.model.Task;
import nl.daan.tasks.util.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateCommand extends Command {

    ArrayList<String> flags = new ArrayList<String>() {{
        add("-t");
        add("-i");
        add("-d");
    }};

    @Override
    public boolean run(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args, ITaskRepository taskRepository) {
        Player p = (Player) commandSender;

        Task task = new Task();

        String title = null;
        String desc = null;
        LocalDate deadline = null;


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
                    case "-d":
                        if(!args[i+1].chars().allMatch( Character::isDigit )) {
                            return false;
                        }
                        int days = Integer.parseInt(args[i+1]);
                        deadline = LocalDate.now().plusDays(days);
                        break;
                }
            }
        }

        if(title == null || desc == null) {
            return false;
        }

        System.out.println(title);
        System.out.println(desc);

        task.title = title;
        task.description = desc;
        task.creator = p;
        task.creatorUUID = p.getUniqueId().toString();
        task.location = p.getLocation();
        task.deadline = deadline;

        taskRepository.insert(task);
        MessageUtil.SendMessage(p,"Task created.");
        return true;
    }
}
