package io.github.tsgrissom.essentialskt.command

import io.github.tsgrissom.pluginapi.command.CommandBase
import io.github.tsgrissom.pluginapi.command.CommandContext
import io.github.tsgrissom.pluginapi.extension.getUniqueString
import io.github.tsgrissom.pluginapi.extension.lacksPermission
import io.github.tsgrissom.pluginapi.extension.sendColored
import io.github.tsgrissom.pluginapi.misc.ClickableText
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil

class UniqueIdCommand : CommandBase() {

    private val perm = "essentials.uniqueid"

    private fun createDisplayUniqueIdAsTextComponents(sender: CommandSender, t: Player) : Array<BaseComponent> {
        val name = t.name
        val uuid = t.getUniqueString()
        val builder = ComponentBuilder()
            .append("UUID of ").color(ChatColor.GRAY)
            .append(name).color(ChatColor.YELLOW)
            .append(" is ").color(ChatColor.GRAY)
            .append(uuid).color(ChatColor.YELLOW)

        if (sender is Player) {
            val clickText = ClickableText
                .compose("&6&lCOPY")
                .action(ClickEvent.Action.COPY_TO_CLIPBOARD)
                .value(uuid)

            builder
                .append("\n")
                .append(clickText.toTextComponent())
        }

        return builder.create()
    }

    override fun execute(context: CommandContext) {
        val args = context.args
        val sender = context.sender

        if (sender.lacksPermission(perm))
            return context.sendNoPermission(sender, perm)

        val t: Player = if (args.size == 1) {
            val sub = args[0]
            Bukkit.getPlayer(sub)
                ?: return sender.sendColored("&4Could not find player &c\"${sub}\"")
        } else {
            if (sender is ConsoleCommandSender)
                return sender.sendColored("&4Console Usage: &c/uuid <Player>")
            if (sender !is Player)
                return

            sender
        }

        val component: Array<BaseComponent> = createDisplayUniqueIdAsTextComponents(sender, t)
        sender.spigot().sendMessage(*component)
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String> {
        val tab = mutableListOf<String>()

        if (sender.lacksPermission(perm))
            return tab

        val len = args.size

        if (len == 1) {
            StringUtil.copyPartialMatches(args[0], getOnlinePlayerNamesToMutableList(), tab)
        }

        return tab.sorted().toMutableList()
    }
}