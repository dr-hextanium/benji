package org.firstinspires.ftc.teamcode.command.auto

import com.arcrobotics.ftclib.command.Command
import com.arcrobotics.ftclib.command.CommandBase
import com.pedropathing.follower.Follower
import com.pedropathing.localization.Pose

class PedroToPoint(
    val follower: Follower,
    val pose: Pose,
    val finished: () -> Boolean = { !follower.isBusy || follower.atParametricEnd() || follower.currentTValue >= T_END },
    val maxPower: (Double) -> Double = { 1.0 }
) : CommandBase(), Command {
    constructor(
        follower: Follower,
        x: Double,
        y: Double,
        heading: Double
    ) : this(follower, Pose(x, y, heading))

    override fun initialize() {
        follower.holdPoint(pose)
    }

    override fun execute() {
        follower.update()
        follower.setMaxPower(maxPower(follower.currentTValue))
    }

    override fun isFinished(): Boolean = finished()

    companion object {
        const val T_END = 0.995
    }
}