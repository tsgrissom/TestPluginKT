package io.github.tsgrissom.pluginapi.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

abstract class CommandBase : CommandExecutor {

    abstract fun execute(context: CommandContext)

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val context = CommandContext(sender=sender, command=command, label=label, args=args)

        execute(context)

        return true
    }
}