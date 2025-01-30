package org.firstinspires.ftc.teamcode.opmode.auto.sample

import com.arcrobotics.ftclib.command.Command
import com.arcrobotics.ftclib.command.CommandScheduler
import com.arcrobotics.ftclib.command.ParallelCommandGroup
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.arcrobotics.ftclib.command.WaitCommand
import com.pedropathing.localization.Pose
import com.pedropathing.pathgen.BezierLine
import com.pedropathing.pathgen.Point
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.command.auto.DepositToBasket
import org.firstinspires.ftc.teamcode.command.auto.PedroPathCommand
import org.firstinspires.ftc.teamcode.command.core.OpenClaw
import org.firstinspires.ftc.teamcode.command.core.VariableElbow
import org.firstinspires.ftc.teamcode.command.core.VariableWrist
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems.back
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems.front
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist
import org.firstinspires.ftc.teamcode.opmode.auto.AutoOpMode
import kotlin.math.PI

@Autonomous
class OneSample : AutoOpMode(Pose(8.65, 109.5, 0.0)) {
    val commands = mutableListOf<Command>()

    override fun paths() {
        val scoreSite = Pose(16.00, 124.0, -PI / 4.0)

        val preload = follower.pathBuilder()
            .addPath(BezierLine(Point(start), Point(scoreSite)))
            .setLinearHeadingInterpolation(start.heading, scoreSite.heading)
            .setZeroPowerAccelerationMultiplier(0.5)
            .build()

        commands.add(
            SequentialCommandGroup(
                ParallelCommandGroup(
                    PedroPathCommand(preload, follower, timer),
                ),
                WaitCommand(1500),
                DepositToBasket(),
                WaitCommand(1500),
                OpenClaw(back.grabber)
            )
        )
    }

    override fun initialize() {
        CommandScheduler.getInstance().schedule(
            VariableWrist(Wrist.BACK_DEFAULT, back.grabber.wrist),
            VariableElbow(Elbow.BACK_TO_DEPOSIT, back.grabber.elbow),
            VariableWrist(Wrist.FRONT_DEFAULT, front.grabber.wrist),
            VariableElbow(Elbow.FRONT_DEFAULT, front.grabber.elbow)
        )
    }

    override fun start() {
        commands.forEach { it.schedule() }
        super.start()
    }

    override fun cycle() {
        telemetry.addData("busy", follower.isBusy)
    }
}