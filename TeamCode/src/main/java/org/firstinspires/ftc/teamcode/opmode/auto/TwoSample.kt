package org.firstinspires.ftc.teamcode.opmode.auto

import com.arcrobotics.ftclib.command.Command
import com.arcrobotics.ftclib.command.CommandScheduler
import com.arcrobotics.ftclib.command.InstantCommand
import com.arcrobotics.ftclib.command.ParallelCommandGroup
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.arcrobotics.ftclib.command.WaitCommand
import com.pedropathing.localization.Pose
import com.pedropathing.pathgen.BezierLine
import com.pedropathing.pathgen.Point
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.command.auto.DepositToBasket
import org.firstinspires.ftc.teamcode.command.auto.DepositToGround
import org.firstinspires.ftc.teamcode.command.auto.PedroPathCommand
import org.firstinspires.ftc.teamcode.command.core.CloseClaw
import org.firstinspires.ftc.teamcode.command.core.ElbowPointsDown
import org.firstinspires.ftc.teamcode.command.core.Intake
import org.firstinspires.ftc.teamcode.command.core.OpenClaw
import org.firstinspires.ftc.teamcode.command.core.Transfer
import org.firstinspires.ftc.teamcode.command.core.VariableElbow
import org.firstinspires.ftc.teamcode.command.core.VariableWrist
import org.firstinspires.ftc.teamcode.command.core.WristPointsDown
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems.back
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems.front
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow
import org.firstinspires.ftc.teamcode.hardware.subsystems.Extendo
import org.firstinspires.ftc.teamcode.hardware.subsystems.Extendo.Companion.TO_INTAKE
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist
import kotlin.math.PI

@Autonomous
class TwoSample : AutoOpMode(Pose(8.65, 109.5, 0.0)) {
    val commands = mutableListOf<Command>()

    override fun paths() {
        val scoreSite = Pose(16.00, 124.0, -PI / 4.0)

        val firstSample = Pose(17.5, 116.5, 0.0)

        val preload = follower.pathBuilder()
            .addPath(BezierLine(Point(start), Point(scoreSite)))
            .setLinearHeadingInterpolation(start.heading, scoreSite.heading)
            .setZeroPowerAccelerationMultiplier(0.5)
            .build()

        val first = follower.pathBuilder()
            .addPath(BezierLine(Point(scoreSite), Point(firstSample)))
            .setLinearHeadingInterpolation(scoreSite.heading, firstSample.heading)
            .setZeroPowerAccelerationMultiplier(0.5)
            .build()

        val second = follower.pathBuilder()
            .addPath(BezierLine(Point(firstSample), Point(scoreSite)))
            .setLinearHeadingInterpolation(firstSample.heading, scoreSite.heading)
            .setZeroPowerAccelerationMultiplier(0.5)
            .build()

        val extendo by lazy { front.extendable as Extendo }

        commands.add(
            SequentialCommandGroup(
                ParallelCommandGroup(
                    PedroPathCommand(preload, follower, timer),
                    SequentialCommandGroup(
                        WaitCommand(1500),
                        DepositToBasket(),
                    )
                ),

                WaitCommand(1000),
                VariableWrist(Wrist.BACK_TO_DEPOSIT, back.wrist),
                VariableElbow(Elbow.BACK_TO_DEPOSIT, back.elbow),

                WaitCommand(500),
                OpenClaw(back.grabber),

                WaitCommand(750),
                VariableWrist(Wrist.BACK_DEFAULT, back.wrist),
                VariableElbow(Elbow.BACK_TO_TRANSFER, back.elbow),

                WaitCommand(1000),
                DepositToGround(),

                WaitCommand(500),
                PedroPathCommand(first, follower, timer),

                WaitCommand(1500),
                InstantCommand({ extendo.target = TO_INTAKE }),

                WaitCommand(1500),
                ParallelCommandGroup(
                    WristPointsDown(front.grabber),
                    ElbowPointsDown(front.grabber),
                ),

                WaitCommand(1000),
                CloseClaw(front.grabber),

                WaitCommand(1000),
                Transfer(),

                WaitCommand(1000),

                ParallelCommandGroup(
                    PedroPathCommand(second, follower, timer),
                    SequentialCommandGroup(
                        WaitCommand(1500),
                        DepositToBasket(),
                    )
                ),

                WaitCommand(1000),
                VariableWrist(Wrist.BACK_TO_DEPOSIT, back.wrist),
                VariableElbow(Elbow.BACK_TO_DEPOSIT, back.elbow),

                WaitCommand(500),
                OpenClaw(back.grabber),

                WaitCommand(750),
                VariableWrist(Wrist.BACK_DEFAULT, back.wrist),
                VariableElbow(Elbow.BACK_TO_TRANSFER, back.elbow),

                WaitCommand(1000),
                DepositToGround(),
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