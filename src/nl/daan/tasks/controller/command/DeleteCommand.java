package nl.daan.tasks.controller.command;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import nl.daan.tasks.model.ITaskRepository;
import nl.daan.tasks.model.Task;
import net.md_5.bungee.api.ChatColor;
import nl.daan.tasks.util.MessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class DeleteCommand extends Command {

    public static ArrayList<String> confirmQueue = new ArrayList<>();

    @Override
    public boolean run(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args, ITaskRepository taskRepository) {
        Player p = (Player) commandSender;

        if(args.length < 2) {
            return false;
        }

        System.out.println(args[0]);

        if(args[0].equals("confirm") || args[0].equals("unconfirm")) {

            if(confirmQueue.contains(p.getName())) {
                if(!args[1].chars().allMatch( Character::isDigit )) {
                    return false;
                }
                if(args[0].equals("confirm")) {
                    int id = Integer.parseInt(args[1]);
                    Task task = taskRepository.getById(id);
                    MessageUtil.SendMessage(p, ChatColor.RED + task.title + "deleted");
                    taskRepository.delete(task);

                    confirmQueue.remove(p.getName());
                    return true;
                } else {
                    MessageUtil.SendMessage(p,"Deletion cancelled");
                    confirmQueue.remove(p.getName());
                    return true;
                }
            } else {
                MessageUtil.SendMessage(p,ChatColor.BLUE+"No confirmation required.");
                return true;
            }
        }

        if(!args[1].chars().allMatch( Character::isDigit )) {
            return false;
        }

        int id = Integer.parseInt(args[1]);

        TextComponent message =  new TextComponent( "Confirm deletion of task.\n" );
        message.setColor(ChatColor.BLUE);
        message.setBold(true);
        TextComponent yes =  new TextComponent( "Yes. " );
        yes.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/task confirm "+id) );
        yes.setColor(ChatColor.AQUA);
        yes.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Confirm deletion of this task").create() ) );

        yes.setBold(true);

        TextComponent no =  new TextComponent( "No." );
        no.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/task unconfirm "+id) );
        no.setColor(net.md_5.bungee.api.ChatColor.DARK_RED);
        no.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Deny deletion of this task").create() ) );

        no.setBold(true);

        message.addExtra(yes);
        message.addExtra(no);
        MessageUtil.SendMessage(p, message);

        if(confirmQueue.contains(p.getName())) {
            confirmQueue.remove(p.getName());
        }

        confirmQueue.add(p.getName());

        return true;
    }
}
