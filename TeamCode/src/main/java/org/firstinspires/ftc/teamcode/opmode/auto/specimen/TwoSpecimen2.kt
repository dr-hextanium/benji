@file:Suppress("IllegalIdentifier")

package org.firstinspires.ftc.teamcode.opmode.auto.specimen

import com.arcrobotics.ftclib.command.Command
import com.arcrobotics.ftclib.command.CommandScheduler
import com.arcrobotics.ftclib.command.ParallelCommandGroup
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.arcrobotics.ftclib.command.WaitCommand
import com.pedropathing.localization.Pose
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.command.auto.DepositToBar
import org.firstinspires.ftc.teamcode.command.auto.DepositToGround
import org.firstinspires.ftc.teamcode.command.auto.PedroPathCommand
import org.firstinspires.ftc.teamcode.command.auto.PedroToPoint
import org.firstinspires.ftc.teamcode.command.core.CloseClaw
import org.firstinspires.ftc.teamcode.command.core.Grab
import org.firstinspires.ftc.teamcode.command.core.LiftTo
import org.firstinspires.ftc.teamcode.command.core.NudgeLift
import org.firstinspires.ftc.teamcode.command.core.OpenClaw
import org.firstinspires.ftc.teamcode.command.core.VariableElbow
import org.firstinspires.ftc.teamcode.command.core.VariableTwist
import org.firstinspires.ftc.teamcode.command.core.VariableWrist
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems.back
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems.front
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow
import org.firstinspires.ftc.teamcode.hardware.subsystems.Lift
import org.firstinspires.ftc.teamcode.hardware.subsystems.Twist
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist
import org.firstinspires.ftc.teamcode.opmode.auto.AutoOpMode
import org.firstinspires.ftc.teamcode.utility.deg
import kotlin.math.PI

@Autonomous
class TwoSpecimen2 : AutoOpMode(Pose(8.5, 65.0, -PI)) {
    val commands = mutableListOf<Command>()

    override fun paths() {
        val `score preload` = Pose(39.0, 69.0, (-180).deg)
        val `go back` = Pose(20.0, 69.0, (-90).deg)
        val `translate to intermediate` = Pose(28.0, 51.0, (-45).deg)
        val `translate to push` = Pose(65.0, 24.0, (-45).deg)
        val `spin to push` = Pose(65.0, 24.0, (0).deg)
        val `first push` = Pose(15.0, 24.0, (0).deg)
        val `come back` = Pose(60.0, 24.0, (0).deg)
        val `go to second push` = Pose(60.0, 12.0, (0).deg)
        val `second push` = Pose(19.0, 17.0, (0).deg)
        val `come back slightly` = Pose(30.0, 17.0, (0).deg)
        val `go back to grab` = Pose(10.0, 17.0, (0).deg)
        val `score first` = Pose(39.0, 75.0, (-180).deg)
        val `back to grab` = Pose(8.5, 35.0, (0).deg)
        val `score second` = Pose(39.0, 70.0, (-180).deg)

        commands.add(
            SequentialCommandGroup(
                SequentialCommandGroup(
                    ParallelCommandGroup(
                        PedroToPoint(follower, `score preload`, maxPower = { 0.8 }).andThen(WaitCommand(1500)),
                        SequentialCommandGroup(
                            WaitCommand(1500),
                            DepositToBar(),
                        )
                    ),

                    WaitCommand(1500),
                    NudgeLift(11, back.extendable as Lift, 1000),
                    OpenClaw(back.grabber, 500),

                    DepositToGround(),
                ),

                WaitCommand(1500),

                PedroToPoint(follower, `go back`).andThen(WaitCommand(1500)),
                PedroToPoint(follower, `translate to intermediate`).andThen(WaitCommand(1500)),
                PedroToPoint(follower, `translate to push`).andThen(WaitCommand(1500)),
                PedroToPoint(follower, `spin to push`).andThen(WaitCommand(1000)),
                PedroToPoint(follower, `first push`).andThen(WaitCommand(1500)),
                PedroToPoint(follower, `come back`).andThen(WaitCommand(1500)),
                PedroToPoint(follower, `go to second push`).andThen(WaitCommand(1000)),
                PedroToPoint(follower, `second push`).andThen(WaitCommand(1000)),
                PedroToPoint(follower, `come back slightly`).andThen(WaitCommand(1000)),

                ParallelCommandGroup(
                    VariableWrist(Wrist.BACK_SPEC_GRAB, back.wrist),
                    VariableElbow(Elbow.BACK_SPEC_GRAB, back.elbow),
                    VariableTwist(0.0, back.grabber)
                ),

                PedroToPoint(follower, `go back to grab`, maxPower = { 0.6 }).alongWith(
                    LiftTo(-1, back.extendable as Lift, 100)
                ).andThen(
                    SequentialCommandGroup(
                        WaitCommand(1500),
                        CloseClaw(back.grabber)
                    )
                ),

                SequentialCommandGroup(
                    ParallelCommandGroup(
                        PedroToPoint(follower, `score first`).andThen(WaitCommand(1000)),
                        SequentialCommandGroup(
                            WaitCommand(1500),
                            DepositToBar(),
                        )
                    ),

                    WaitCommand(1500),
                    NudgeLift(11, back.extendable as Lift, 1000),
                    OpenClaw(back.grabber, 500),

                    DepositToGround(),
                ),



//                PedroToPoint(follower, `back to grab`).andThen(WaitCommand(1000)),
//                PedroToPoint(follower, `score second`).andThen(WaitCommand(1000)),
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