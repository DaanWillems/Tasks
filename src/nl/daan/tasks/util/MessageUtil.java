package nl.daan.tasks.util;

        import net.md_5.bungee.api.ChatColor;
        import net.md_5.bungee.api.chat.TextComponent;
        import org.bukkit.entity.Player;

public class MessageUtil {
    public static void SendMessage(Player player, String messageString) {
        TextComponent taskPrefix =  new TextComponent(ChatColor.BLUE+"["+ChatColor.AQUA+"Task"+ChatColor.BLUE+"] ");
        taskPrefix.setBold(true);
        TextComponent content =  new TextComponent(ChatColor.BLUE+messageString);
        TextComponent message =  new TextComponent();
        message.addExtra(taskPrefix);
        message.addExtra(content);
        player.spigot().sendMessage(message);
    }

    public static void SendMessage(Player player, TextComponent message) {
        TextComponent taskPrefix =  new TextComponent(ChatColor.BLUE+"["+ChatColor.AQUA+"Task"+ChatColor.BLUE+"] ");
        taskPrefix.setBold(true);
        taskPrefix.addExtra(message);
        player.spigot().sendMessage(taskPrefix);
    }
}
