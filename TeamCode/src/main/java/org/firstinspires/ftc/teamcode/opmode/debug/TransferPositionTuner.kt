package org.firstinspires.ftc.teamcode.opmode.debug

import com.arcrobotics.ftclib.command.CommandScheduler
import com.arcrobotics.ftclib.command.ConditionalCommand
import com.arcrobotics.ftclib.command.ParallelCommandGroup
import com.arcrobotics.ftclib.command.button.GamepadButton
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.command.core.CloseClaw
import org.firstinspires.ftc.teamcode.command.core.LiftTo
import org.firstinspires.ftc.teamcode.command.core.OpenClaw
import org.firstinspires.ftc.teamcode.command.core.VariableElbow
import org.firstinspires.ftc.teamcode.command.core.VariableTwist
import org.firstinspires.ftc.teamcode.command.core.VariableWrist
import org.firstinspires.ftc.teamcode.hardware.Globals
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.subsystems.IPositionable
import org.firstinspires.ftc.teamcode.opmode.BasedOpMode
import org.firstinspires.ftc.teamcode.hardware.Globals.Bounds.Front
import org.firstinspires.ftc.teamcode.hardware.subsystems.Claw
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow
import org.firstinspires.ftc.teamcode.hardware.subsystems.Lift
import org.firstinspires.ftc.teamcode.hardware.subsystems.Twist
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist

@TeleOp(group = "Debug")
class TransferPositionTuner : BasedOpMode() {
    private var frontElbowPosition = 0.5
    private var frontWristPosition = 0.5
    private var backElbowPosition = 0.5
    private var backWristPosition = 0.5

    private val frontElbow: IPositionable by lazy { Robot.Subsystems.front.elbow }
    private val frontWrist: IPositionable by lazy { Robot.Subsystems.front.wrist }
    private val backElbow: IPositionable by lazy { Robot.Subsystems.back.elbow }
    private val backWrist: IPositionable by lazy { Robot.Subsystems.back.wrist }


    override fun initialize() {
        //TODO: Do back as well
        frontElbow.bound(Front.elbow)
        frontWrist.bound(Front.wrist)
        backElbow.bound(Globals.Bounds.Back.elbow)
        backWrist.bound(Globals.Bounds.Back.wrist)

        ParallelCommandGroup(
            VariableTwist(Twist.MIDDLE, Robot.Subsystems.front.grabber),
            VariableTwist(Twist.MIDDLE, Robot.Subsystems.back.grabber)
        )

        GamepadButton(Robot.gamepad1, GamepadKeys.Button.LEFT_BUMPER).whenPressed(
            ConditionalCommand(
                OpenClaw(Robot.Subsystems.front.grabber),
                CloseClaw(Robot.Subsystems.front.grabber)
            )
            { Robot.Subsystems.front.grabber.claw.position == Claw.CLOSED }
        )

        GamepadButton(Robot.gamepad1, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
            ConditionalCommand(
                OpenClaw(Robot.Subsystems.back.grabber),
                CloseClaw(Robot.Subsystems.back.grabber)
            )
            { Robot.Subsystems.back.grabber.claw.position == Claw.CLOSED }
        )
    }

    override fun cycle() {
        frontElbowPosition += when {
            gamepad1.dpad_up -> 0.001
            gamepad1.dpad_down -> -0.001
            else -> 0.0
        }

        frontWristPosition += when {
            gamepad1.dpad_right -> 0.001
            gamepad1.dpad_left -> -0.001
            else -> 0.0
        }

        backElbowPosition += when {
            gamepad1.y -> 0.001
            gamepad1.a -> -0.001
            else -> 0.0
        }

        backWristPosition += when {
            gamepad1.b -> 0.001
            gamepad1.x -> -0.001
            else -> 0.0
        }

        frontElbow.position = frontElbowPosition
        frontWrist.position = frontWristPosition

        backElbow.position = backElbowPosition
        backWrist.position = backWristPosition

        telemetry.addData("front elbow position", frontElbow.position)
        telemetry.addData("front wrist position", frontWrist.position)
        telemetry.addData("back elbow position", backElbow.position)
        telemetry.addData("back wrist position", backWrist.position)
    }
}