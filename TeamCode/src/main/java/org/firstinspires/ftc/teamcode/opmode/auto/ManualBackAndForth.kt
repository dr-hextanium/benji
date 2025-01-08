package org.firstinspires.ftc.teamcode.opmode.auto

import com.arcrobotics.ftclib.command.CommandScheduler
import com.arcrobotics.ftclib.command.button.GamepadButton
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D
import org.firstinspires.ftc.teamcode.command.auto.SquIDDriveCommand
import org.firstinspires.ftc.teamcode.command.core.OpenClaw
import org.firstinspires.ftc.teamcode.command.core.VariableElbow
import org.firstinspires.ftc.teamcode.command.core.VariableWrist
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist
import org.firstinspires.ftc.teamcode.opmode.BasedOpMode

@Autonomous
class ManualBackAndForth : BasedOpMode() {
    override fun initialize() {
        val front = Robot.Subsystems.front
        val back = Robot.Subsystems.back
        val gamepad = Robot.gamepad1

        GamepadButton(gamepad, GamepadKeys.Button.DPAD_UP)
            .whenPressed(
                SquIDDriveCommand(Robot.Subsystems.otos, Robot.Subsystems.autoDrive, 0.0, 0.0 ,0.0)
                    .setTarget(Pose2D(DistanceUnit.INCH, 0.0, 0.0, AngleUnit.DEGREES, 0.0))
                    .setLinearTolerance(0.25)
                    .setAngularTolerance(1.0)
            )

        GamepadButton(gamepad, GamepadKeys.Button.DPAD_DOWN)
            .whenPressed(
                SquIDDriveCommand(Robot.Subsystems.otos, Robot.Subsystems.autoDrive, 0.0, 0.0 ,0.0)
                    .setTarget(Pose2D(DistanceUnit.INCH, 50.0, 0.0, AngleUnit.DEGREES, 0.0))
                    .setLinearTolerance(0.25)
                    .setAngularTolerance(1.0)
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
        telemetry.addData("total current", Robot.Motors.all().sumOf { it.getCurrent(CurrentUnit.AMPS) })
        telemetry.addData("pose", Robot.Subsystems.otos.getPose())
    }
}