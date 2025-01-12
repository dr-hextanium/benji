package org.firstinspires.ftc.teamcode.command.auto

import com.arcrobotics.ftclib.command.Command
import com.arcrobotics.ftclib.command.CommandBase
import com.pedropathing.follower.Follower
import com.pedropathing.pathgen.PathChain
import com.qualcomm.robotcore.util.ElapsedTime

class PedroPathCommand(
    val path: PathChain,
    val follower: Follower,
    val time: ElapsedTime,
    val finished: () -> Boolean = { follower.isBusy }
) : CommandBase(), Command {
    override fun initialize() = follower.followPath(path, true)

    override fun execute() = follower.update()

    override fun isFinished() = finished()
}