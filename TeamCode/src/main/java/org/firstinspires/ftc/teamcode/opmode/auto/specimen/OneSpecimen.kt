package org.firstinspires.ftc.teamcode.opmode.auto.specimen

import com.arcrobotics.ftclib.command.Command
import com.arcrobotics.ftclib.command.CommandScheduler
import com.arcrobotics.ftclib.command.ParallelCommandGroup
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.arcrobotics.ftclib.command.WaitCommand
import com.pedropathing.localization.Pose
import com.pedropathing.pathgen.BezierLine
import com.pedropathing.pathgen.Point
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.command.auto.DepositToBar
import org.firstinspires.ftc.teamcode.command.auto.DepositToGround
import org.firstinspires.ftc.teamcode.command.auto.PedroPathCommand
import org.firstinspires.ftc.teamcode.command.core.NudgeLift
import org.firstinspires.ftc.teamcode.command.core.OpenClaw
import org.firstinspires.ftc.teamcode.command.core.VariableElbow
import org.firstinspires.ftc.teamcode.command.core.VariableWrist
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems.back
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems.front
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow
import org.firstinspires.ftc.teamcode.hardware.subsystems.Extendo
import org.firstinspires.ftc.teamcode.hardware.subsystems.Lift
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist
import org.firstinspires.ftc.teamcode.opmode.auto.AutoOpMode
import kotlin.math.PI

@Autonomous
class OneSpecimen : AutoOpMode(Pose(8.5, 86.5, PI / 2.0)) {
    val commands = mutableListOf<Command>()

    override fun paths() {
        val scoreSite = Pose(39.0, 72.0, PI)
        val endPoint = Pose(19.75, 121.0, 0.0)

        val preload = follower.pathBuilder()
            .addPath(BezierLine(Point(start), Point(scoreSite)))
            .setLinearHeadingInterpolation(start.heading, scoreSite.heading)
            .setZeroPowerAccelerationMultiplier(0.5)
            .build()

        val end = follower.pathBuilder()
            .addPath(BezierLine(Point(scoreSite), Point(endPoint)))
            .setLinearHeadingInterpolation(PI, 0.0)
            .setZeroPowerAccelerationMultiplier(0.5)
            .build()

        val extendo by lazy { front.extendable as Extendo }

        commands.add(
            SequentialCommandGroup(
                ParallelCommandGroup(
                    PedroPathCommand(preload, follower, timer),
                    SequentialCommandGroup(
                        WaitCommand(1500),
                        DepositToBar(),
                    )
                ),

                WaitCommand(1500),
                NudgeLift(10, back.extendable as Lift, 1000),
                OpenClaw(back.grabber, 500),

                DepositToGround(),
                WaitCommand(1000),
//
                PedroPathCommand(end, follower, timer),
//                WaitCommand(5000),
//
//                ParallelCommandGroup(
//                    ExtendoTo(TO_INTAKE, extendo),
//                    VariableWrist(Wrist.FRONT_INTERMEDIATE, front.grabber.wrist),
//                    VariableElbow(Elbow.FRONT_INTERMEDIATE, front.grabber.elbow)
//                ),
//                WaitCommand(1000),
//                Intake(extendo)
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