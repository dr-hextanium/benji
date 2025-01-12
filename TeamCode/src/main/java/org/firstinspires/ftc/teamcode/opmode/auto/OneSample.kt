package org.firstinspires.ftc.teamcode.opmode.auto

import com.arcrobotics.ftclib.command.Command
import com.arcrobotics.ftclib.command.ParallelCommandGroup
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.arcrobotics.ftclib.command.WaitCommand
import com.pedropathing.localization.Pose
import com.pedropathing.pathgen.BezierLine
import com.pedropathing.pathgen.Point
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.command.auto.DepositToBasketCommand
import org.firstinspires.ftc.teamcode.command.auto.PedroPathCommand
import org.firstinspires.ftc.teamcode.command.core.CloseClaw
import org.firstinspires.ftc.teamcode.command.core.OpenClaw
import org.firstinspires.ftc.teamcode.hardware.Robot
import kotlin.math.PI

@Autonomous
class OneSample : AutoOpMode(Pose(8.65, 109.5, 0.0)) {
    val commands = mutableListOf<Command>()

    override fun paths() {
        val scoreSite = Pose(15.00, 125.0, -PI / 4.0)

        val preload = follower.pathBuilder()
            .addPath(BezierLine(Point(start), Point(scoreSite)))
            .setLinearHeadingInterpolation(start.heading, scoreSite.heading)
            .setZeroPowerAccelerationMultiplier(0.5)
            .build()

        commands.add(
            SequentialCommandGroup(
                ParallelCommandGroup(
                    PedroPathCommand(preload, follower, timer),
                    DepositToBasketCommand()
                ),

                WaitCommand(1000),

                OpenClaw(Robot.Subsystems.back.grabber)
            )
        )
    }

    override fun initialize() {
        CloseClaw(Robot.Subsystems.back.grabber).schedule()
    }

    override fun start() {
        commands.forEach { it.schedule() }
        super.start()
    }

    override fun cycle() {
        telemetry.addData("busy", follower.isBusy)
    }
}