package org.firstinspires.ftc.teamcode.opmode.auto

import com.acmerobotics.dashboard.config.Config
import com.arcrobotics.ftclib.command.CommandScheduler
import com.arcrobotics.ftclib.command.button.GamepadButton
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.arcrobotics.ftclib.geometry.Pose2d
import com.arcrobotics.ftclib.geometry.Rotation2d
import com.pedropathing.localization.Pose
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D
import org.firstinspires.ftc.teamcode.command.auto.GoToPointCommand
import org.firstinspires.ftc.teamcode.command.core.OpenClaw
import org.firstinspires.ftc.teamcode.command.core.VariableElbow
import org.firstinspires.ftc.teamcode.command.core.VariableWrist
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist
import org.firstinspires.ftc.teamcode.opmode.BasedOpMode

@Autonomous
class ManualBackAndForth : AutoOpMode(Pose(0.0, 0.0, 0.0)) {
    override fun paths() {}

    override fun initialize() {
        val front = Robot.Subsystems.front
        val back = Robot.Subsystems.back
        val gamepad = Robot.gamepad1

        GamepadButton(gamepad, GamepadKeys.Button.DPAD_UP)
            .whenPressed(
                GoToPointCommand(
                    Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
                    Robot.Subsystems.drive,
                    Robot.Subsystems.otos
                )
            )

        GamepadButton(gamepad, GamepadKeys.Button.DPAD_UP)
            .whenPressed(
                GoToPointCommand(
                    Pose2d(0.0, 25.0, Rotation2d.fromDegrees(0.0)),
                    Robot.Subsystems.drive,
                    Robot.Subsystems.otos
                )
            )

        CommandScheduler.getInstance().schedule(
            OpenClaw(back.grabber),
            OpenClaw(front.grabber),
            VariableWrist(Wrist.BACK_DEFAULT, back.grabber.wrist),
            VariableElbow(Elbow.BACK_TO_DEPOSIT, back.grabber.elbow),
            VariableWrist(Wrist.FRONT_DEFAULT, front.grabber.wrist),
            VariableElbow(Elbow.FRONT_DEFAULT, front.grabber.elbow)
        )
    }

    override fun cycle() {
        val pose = Robot.Subsystems.otos.getPose()

        val x = pose.x
        val y = pose.y
        val heading = pose.heading

        telemetry.addData("x", x)
        telemetry.addData("y", y)
        telemetry.addData("heading", heading)
    }
}