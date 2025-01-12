package org.firstinspires.ftc.teamcode.opmode.debug

import com.arcrobotics.ftclib.command.CommandScheduler
import com.arcrobotics.ftclib.command.ConditionalCommand
import com.arcrobotics.ftclib.command.ParallelCommandGroup
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.arcrobotics.ftclib.command.WaitCommand
import com.arcrobotics.ftclib.command.button.GamepadButton
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.command.core.CloseClaw
import org.firstinspires.ftc.teamcode.command.core.ElbowPointsDown
import org.firstinspires.ftc.teamcode.command.core.ElbowToDefault
import org.firstinspires.ftc.teamcode.command.core.ElbowToTransfer
import org.firstinspires.ftc.teamcode.command.core.Intake
import org.firstinspires.ftc.teamcode.command.core.OpenClaw
import org.firstinspires.ftc.teamcode.command.core.VariableElbow
import org.firstinspires.ftc.teamcode.command.core.VariableTwist
import org.firstinspires.ftc.teamcode.command.core.VariableWrist
import org.firstinspires.ftc.teamcode.command.core.WristPointsDown
import org.firstinspires.ftc.teamcode.command.core.WristToTransfer
import org.firstinspires.ftc.teamcode.hardware.Globals
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.subsystems.IPositionable
import org.firstinspires.ftc.teamcode.opmode.BasedOpMode
import org.firstinspires.ftc.teamcode.hardware.Globals.Bounds.Front
import org.firstinspires.ftc.teamcode.hardware.subsystems.Claw
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow
import org.firstinspires.ftc.teamcode.hardware.subsystems.Extendo
import org.firstinspires.ftc.teamcode.hardware.subsystems.Twist
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist
import org.firstinspires.ftc.teamcode.opmode.CommandSequencing.Companion.SQUARE
import org.firstinspires.ftc.teamcode.opmode.CommandSequencing.Companion.TRIANGLE


@TeleOp(group = "Debug")
class IntermediatePositionFinder : BasedOpMode() {
    private var elbowPosition = 0.5
    private var wristPosition = 0.5

    private val elbow: IPositionable by lazy { Robot.Subsystems.front.elbow }
    private val wrist: IPositionable by lazy { Robot.Subsystems.front.wrist }

    val extendo by lazy { Robot.Subsystems.front.extendable as Extendo }

    override fun initialize() {
        //TODO: Do back as well
        elbow.bound(Front.elbow)
        wrist.bound(Front.wrist)

        CommandScheduler.getInstance().schedule(
            VariableWrist(Wrist.BACK_TO_TRANSFER, Robot.Subsystems.back.grabber.wrist),
            VariableElbow(Elbow.BACK_TO_TRANSFER, Robot.Subsystems.back.grabber.elbow),
            VariableTwist(Twist.MIDDLE, Robot.Subsystems.back.grabber),
            VariableTwist(Twist.MIDDLE, Robot.Subsystems.front.grabber)
        )
        CommandScheduler.getInstance().run()

        GamepadButton(Robot.gamepad1, SQUARE).whenPressed(
            SequentialCommandGroup(
                OpenClaw(Robot.Subsystems.front.grabber, 100),
                ParallelCommandGroup(
                    WristPointsDown(Robot.Subsystems.front.grabber),
                    ElbowPointsDown(Robot.Subsystems.front.grabber)
                ),
                WaitCommand(500),
                CloseClaw(Robot.Subsystems.front.grabber),
                WaitCommand(300),
                ParallelCommandGroup(
                    WristToTransfer(Robot.Subsystems.front.grabber),
                    ElbowToTransfer(Robot.Subsystems.front.grabber)
                )
            )
        )
        GamepadButton(Robot.gamepad1, TRIANGLE).whenPressed(
            ConditionalCommand(
                CloseClaw(Robot.Subsystems.front.grabber),
                OpenClaw(Robot.Subsystems.front.grabber)
            ) { Robot.Subsystems.front.grabber.claw.position == Claw.OPEN }
        )
    }

    override fun cycle() {
        elbowPosition += when {
            gamepad1.dpad_up -> 0.001
            gamepad1.dpad_down -> -0.001
            else -> 0.0
        }

        wristPosition += when {
            gamepad1.dpad_right -> 0.001
            gamepad1.dpad_left -> -0.001
            else -> 0.0
        }

        elbow.position = elbowPosition
        wrist.position = wristPosition

        telemetry.addData("elbow position", elbow.position)
        telemetry.addData("wrist position", wrist.position)
    }
}