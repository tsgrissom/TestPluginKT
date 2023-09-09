package io.github.tsgrissom.testpluginkt.command

import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class CommandContext(
    val sender: CommandSender,
    val command: Command,
    val label: String,
    val args: Array<out String>?
    ) {
}