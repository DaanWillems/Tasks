package nl.daan.tasks.controller;

import nl.daan.tasks.controller.command.CommandManager;
import nl.daan.tasks.model.DummyTaskRepository;
import nl.daan.tasks.model.ITaskRepository;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        super.onEnable();

        ITaskRepository taskRepository = new DummyTaskRepository();

        CommandManager commandManager = new CommandManager(this, taskRepository);
        commandManager.registerCommands();

        this.getCommand("task").setExecutor(commandManager);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
