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
import org.firstinspires.ftc.teamcode.command.auto.PrepGrabbingSpecimen
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
class TwoSpecimen3 : AutoOpMode(Pose(8.5, 65.0, -PI)) {
    val commands = mutableListOf<Command>()

    override fun paths() {
        val `score preload` = Pose(39.0, 78.0, (-180).deg)
        val `go to grab` = Pose(15.0, 30.0, (0).deg)
        val `grab slowly` = Pose(10.0, 30.0, (0).deg)
        val `score second` = Pose(39.0, 65.0, (-180).deg)

        commands.add(
            SequentialCommandGroup(
                SequentialCommandGroup(),
                PedroToPoint(follower, `go to grab`),
                PedroToPoint(follower, `grab slowly`),
                PedroToPoint(follower, `score second`),
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