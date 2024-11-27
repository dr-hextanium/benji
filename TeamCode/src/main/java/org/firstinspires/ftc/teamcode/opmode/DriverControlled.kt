package org.firstinspires.ftc.teamcode.opmode

import com.arcrobotics.ftclib.command.CommandScheduler
import com.arcrobotics.ftclib.command.ParallelCommandGroup
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.arcrobotics.ftclib.command.WaitCommand
import com.arcrobotics.ftclib.command.button.GamepadButton
import com.arcrobotics.ftclib.command.button.Trigger
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.arcrobotics.ftclib.gamepad.TriggerReader
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.command.core.CloseClaw
import org.firstinspires.ftc.teamcode.command.core.ElbowToTransfer
import org.firstinspires.ftc.teamcode.command.core.OpenClaw
import org.firstinspires.ftc.teamcode.command.core.VariableElbow
import org.firstinspires.ftc.teamcode.command.core.VariableTwist
import org.firstinspires.ftc.teamcode.command.core.VariableWrist
import org.firstinspires.ftc.teamcode.command.core.WristToTransfer
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist

@TeleOp
class DriverControlled : BasedOpMode() {
	override fun initialize() {
		val front = Robot.Subsystems.front
		val back = Robot.Subsystems.back
		val gamepad = Robot.gamepad1

		CommandScheduler.getInstance().schedule(
			OpenClaw(back.grabber),
			OpenClaw(front.grabber),
			VariableWrist(Wrist.BACK_DEFAULT, back.grabber.wrist),
			VariableElbow(Elbow.BACK_TO_DEPOSIT, back.grabber.elbow),
			VariableWrist(Wrist.FRONT_DEFAULT, front.grabber.wrist),
			VariableElbow(Elbow.FRONT_DEFAULT, front.grabber.elbow)
		)

		CommandScheduler.getInstance().run()

		GamepadButton(gamepad, GamepadKeys.Button.X).whenPressed(
//			SequentialCommandGroup(
//				VariableWrist(Wrist.FRONT_DOWN, front.grabber.wrist, 300),
//				VariableElbow(Elbow.FRONT_DOWN, front.grabber.elbow),
//			)
			ParallelCommandGroup(
				VariableWrist(Wrist.FRONT_DOWN, front.grabber.wrist),
				VariableElbow(Elbow.FRONT_DOWN, front.grabber.elbow)
			)
		)

		GamepadButton(gamepad, GamepadKeys.Button.Y).whenPressed(
			SequentialCommandGroup(
				CloseClaw(front.grabber, 500),
				ParallelCommandGroup(
					VariableWrist(Wrist.FRONT_TO_TRANSFER, front.grabber.wrist),
					VariableElbow(Elbow.FRONT_TO_TRANSFER, front.grabber.elbow),
					VariableWrist(Wrist.BACK_TO_TRANSFER, back.grabber.wrist),
					VariableElbow(Elbow.BACK_TO_TRANSFER, back.grabber.elbow),
				)
			)
		)

		GamepadButton(gamepad, GamepadKeys.Button.A).whenPressed(
			SequentialCommandGroup(
				CloseClaw(back.grabber, 500),
				OpenClaw(front.grabber, 250),
				ParallelCommandGroup(
					VariableWrist(Wrist.BACK_DEFAULT, back.grabber.wrist),
					VariableElbow(Elbow.BACK_TO_DEPOSIT, back.grabber.elbow)
				)
			)
		)

		GamepadButton(gamepad, GamepadKeys.Button.B).whenPressed(
			SequentialCommandGroup(
				VariableWrist(Wrist.BACK_TO_DEPOSIT, back.grabber.wrist),
				VariableElbow(Elbow.BACK_TO_DEPOSIT, back.grabber.elbow, 500),
				OpenClaw(back.grabber)
			)
		)

		GamepadButton(gamepad, GamepadKeys.Button.LEFT_BUMPER).whenPressed(
			SequentialCommandGroup(
				VariableElbow(Elbow.BACK_TO_DEPOSIT, back.grabber.elbow, 1000),
				VariableWrist(Wrist.BACK_TO_DEPOSIT, back.grabber.wrist)
			)
		)

		GamepadButton(gamepad, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
			OpenClaw(back.grabber)
		)
	}

	override fun cycle() {

	}
}