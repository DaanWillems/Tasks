package nl.daan.tasks.controller.command;

import nl.daan.tasks.controller.Main;
import nl.daan.tasks.model.ITaskRepository;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class CommandManager implements CommandExecutor {

    private ITaskRepository taskRepository;
    private Main main;
    private Map<String, Command> commandMap;

    public CommandManager(Main main, ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.main = main;
    }

    public void registerCommands() {
        commandMap = new HashMap<>();
        commandMap.put("create", new CreateCommand());
        commandMap.put("help", new HelpCommand());
        commandMap.put("list", new ListCommand());
        commandMap.put("delete", new DeleteCommand());
        commandMap.put("solve", new SolveCommand());
        commandMap.put("assign", new AssignCommand());
        commandMap.put("teleport", new TeleportCommand());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        if(args.length < 1) {
            usage(commandSender);
            return true;
        }

        Command cmd = commandMap.get(args[0]);

        if(cmd == null) {
            notFound(commandSender);
            return true;
        }

        //TODO: Add check if the command can be executed by a player/console or both
        if(!commandSender.hasPermission(cmd.permissionRequired())) {
            //   noPermission(commandSender);
            //   return true;
        }

        if(!cmd.run(commandSender, command, label, args, taskRepository)){
            usage(commandSender);
        }
        return true;
    }

    private void notFound(CommandSender commandSender) {
        commandSender.sendMessage(ChatColor.RED+"Incorrect usage! Type '/task help' for help");
    }

    private void usage(CommandSender commandSender) {
        commandSender.sendMessage(ChatColor.RED+"Incorrect usage! Type '/task help' for help");
    }

    private void noPermission(CommandSender commandSender) {
        commandSender.sendMessage(ChatColor.RED+"You don't have permission to use this command!");
    }
}
