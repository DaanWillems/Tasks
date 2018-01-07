package nl.daan.tasks.controller;

import nl.daan.tasks.controller.command.CommandManager;
import nl.daan.tasks.events.PlayerJoinEvent;
import nl.daan.tasks.model.*;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        super.onEnable();

        ITaskRepository taskRepository = new TaskRepository();

        CommandManager commandManager = new CommandManager(this, taskRepository);
        commandManager.registerCommands();
        getServer().getPluginManager().registerEvents(new PlayerJoinEvent(taskRepository), this);
        this.getCommand("task").setExecutor(commandManager);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
