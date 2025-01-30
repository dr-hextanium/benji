@file:Suppress("IllegalIdentifier")

package org.firstinspires.ftc.teamcode.opmode.auto.specimen

import com.arcrobotics.ftclib.command.Command
import com.arcrobotics.ftclib.command.CommandScheduler
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.arcrobotics.ftclib.command.WaitCommand
import com.pedropathing.localization.Pose
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.command.auto.PedroToPoint
import org.firstinspires.ftc.teamcode.command.core.VariableElbow
import org.firstinspires.ftc.teamcode.command.core.VariableWrist
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems.back
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems.front
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist
import org.firstinspires.ftc.teamcode.opmode.auto.AutoOpMode
import org.firstinspires.ftc.teamcode.utility.deg
import kotlin.math.PI

@Autonomous
class TwoSpecimen2 : AutoOpMode(Pose(8.5, 65.0, -PI)) {
    val commands = mutableListOf<Command>()

    override fun paths() {
        val `score preload` = Pose(39.0, 65.0, (-180).deg)
        val `go back` = Pose(39.0, 65.0, (-180).deg)
        val `translate to push` = Pose(39.0, 65.0, (-180).deg)
        val `spin to push` = Pose(39.0, 65.0, (-180).deg)
        val `first push` = Pose(39.0, 65.0, (-180).deg)
        val `come back` = Pose(39.0, 65.0, (-180).deg)
        val `go to second push` = Pose(39.0, 65.0, (-180).deg)
        val `second push` = Pose(39.0, 65.0, (-180).deg)
        val `come back slightly` = Pose(39.0, 65.0, (-180).deg)
        val `go back to grab` = Pose(39.0, 65.0, (-180).deg)
        val `score first` = Pose(39.0, 65.0, (-180).deg)
        val `back to grab` = Pose(39.0, 65.0, (-180).deg)
        val `score second` = Pose(39.0, 65.0, (-180).deg)

        commands.add(
            SequentialCommandGroup(
                PedroToPoint(follower, `score preload`).andThen(WaitCommand(1000)),
                PedroToPoint(follower, `go back`).andThen(WaitCommand(1000)),
                PedroToPoint(follower, `translate to push`).andThen(WaitCommand(1000)),
                PedroToPoint(follower, `spin to push`).andThen(WaitCommand(1000)),
                PedroToPoint(follower, `first push`).andThen(WaitCommand(1000)),
                PedroToPoint(follower, `come back`).andThen(WaitCommand(1000)),
                PedroToPoint(follower, `go to second push`).andThen(WaitCommand(1000)),
                PedroToPoint(follower, `second push`).andThen(WaitCommand(1000)),
                PedroToPoint(follower, `come back slightly`).andThen(WaitCommand(1000)),
                PedroToPoint(follower, `go back to grab`).andThen(WaitCommand(1000)),
                PedroToPoint(follower, `score first`).andThen(WaitCommand(1000)),
                PedroToPoint(follower, `back to grab`).andThen(WaitCommand(1000)),
                PedroToPoint(follower, `score second`).andThen(WaitCommand(1000)),
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