package org.firstinspires.ftc.teamcode.opmode

import com.arcrobotics.ftclib.command.Command
import com.arcrobotics.ftclib.command.CommandScheduler
import com.arcrobotics.ftclib.command.InstantCommand
import com.arcrobotics.ftclib.command.ParallelCommandGroup
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.arcrobotics.ftclib.command.WaitCommand
import com.arcrobotics.ftclib.command.button.GamepadButton
import com.arcrobotics.ftclib.command.button.Trigger
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.arcrobotics.ftclib.gamepad.TriggerReader
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.command.core.CloseClaw
import org.firstinspires.ftc.teamcode.command.core.ElbowPointsDown
import org.firstinspires.ftc.teamcode.command.core.ElbowToDefault
import org.firstinspires.ftc.teamcode.command.core.ElbowToTransfer
import org.firstinspires.ftc.teamcode.command.core.OpenClaw
import org.firstinspires.ftc.teamcode.command.core.VariableElbow
import org.firstinspires.ftc.teamcode.command.core.VariableWrist
import org.firstinspires.ftc.teamcode.command.core.WristPointsDown
import org.firstinspires.ftc.teamcode.command.core.WristToDefault
import org.firstinspires.ftc.teamcode.command.core.WristToTransfer
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist

@TeleOp
class CommandsImplementation : BasedOpMode() {
	override fun initialize() {
		val front = Robot.Subsystems.front
		val back = Robot.Subsystems.back

		GamepadButton(Robot.gamepad1, GamepadKeys.Button.X)
			.whenPressed(OpenClaw(Robot.Subsystems.front.grabber))
		GamepadButton(Robot.gamepad1, GamepadKeys.Button.Y)
			.whenPressed(CloseClaw(Robot.Subsystems.front.grabber))

		GamepadButton(Robot.gamepad1, GamepadKeys.Button.A).whenPressed(OpenClaw(back.grabber))
		GamepadButton(Robot.gamepad1, GamepadKeys.Button.B).whenPressed(CloseClaw(back.grabber))
//		GamepadButton(Robot.gamepad1, GamepadKeys.Button.LEFT_BUMPER).whenPressed(WristToTransfer(front.grabber))
//		GamepadButton(Robot.gamepad1, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(WristPointsDown(front.grabber))

		GamepadButton(Robot.gamepad1, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
			SequentialCommandGroup(
				CloseClaw(back.grabber, 500),
				OpenClaw(front.grabber, 500),
				ParallelCommandGroup(
					VariableElbow(Elbow.BACK_TO_DEPOSIT, back.grabber.elbow),
					VariableWrist(Wrist.BACK_TO_DEPOSIT, back.grabber.wrist)
				)
			)
		)

		GamepadButton(Robot.gamepad1, GamepadKeys.Button.DPAD_LEFT).whenPressed(VariableElbow(Elbow.BACK_TO_DEPOSIT, back.grabber.elbow))
		GamepadButton(Robot.gamepad1, GamepadKeys.Button.DPAD_RIGHT).whenPressed(VariableElbow(Elbow.BACK_TO_TRANSFER, back.grabber.elbow))

		GamepadButton(Robot.gamepad1, GamepadKeys.Button.DPAD_UP).whenPressed(VariableWrist(Wrist.BACK_TO_DEPOSIT, back.grabber.wrist))
		GamepadButton(Robot.gamepad1, GamepadKeys.Button.DPAD_DOWN).whenPressed(VariableWrist(Wrist.BACK_TO_DEPOSIT, back.grabber.wrist))

//		Trigger { TriggerReader(Robot.gamepad1, GamepadKeys.Trigger.LEFT_TRIGGER).isDown }
//			.whileActiveContinuous(
//				ParallelCommandGroup(
//					VariableElbow(Elbow.BACK_TO_DEPOSIT, back.grabber.elbow),
//					VariableWrist(Wrist.BACK_TO_DEPOSIT, back.grabber.wrist),
//				)
//			)
//			.whenInactive(
//				ParallelCommandGroup(
//					VariableElbow(Elbow.BACK_TO_TRANSFER, back.grabber.elbow),
//					VariableWrist(Wrist.BACK_TO_TRANSFER, back.grabber.wrist)
//				)
//			)

		Trigger { TriggerReader(Robot.gamepad1, GamepadKeys.Trigger.RIGHT_TRIGGER).isDown }
			.whileActiveContinuous(
				ParallelCommandGroup(
					ElbowToTransfer(front.grabber),
					WristToTransfer(front.grabber),
					VariableElbow(Elbow.BACK_TO_TRANSFER, back.grabber.elbow),
					VariableWrist(Wrist.BACK_TO_TRANSFER, back.grabber.wrist)
				)
			)
			.whenInactive(
				ParallelCommandGroup(
					ElbowToDefault(front.grabber),
					WristPointsDown(front.grabber),
					VariableElbow(Elbow.BACK_TO_TRANSFER, back.grabber.elbow),
					VariableWrist(Wrist.BACK_TO_TRANSFER, back.grabber.wrist)
				)
			)

		Trigger { TriggerReader(Robot.gamepad1, GamepadKeys.Trigger.LEFT_TRIGGER).isDown }
			.whileActiveContinuous(
				SequentialCommandGroup(
//					VariableWrist(Wrist.FRONT_DOWN, front.grabber.wrist),
//					WaitCommand(250),
					VariableElbow(Elbow.FRONT_DOWN, front.grabber.elbow),
//					CloseClawBase(front.grabber.claw)
				)
			)
			.whenInactive(
				ParallelCommandGroup(
					ElbowToDefault(front.grabber),
					WristPointsDown(front.grabber),
					VariableElbow(Elbow.BACK_TO_TRANSFER, back.grabber.elbow),
					VariableWrist(Wrist.BACK_TO_TRANSFER, back.grabber.wrist)
				)
			)
//		front.grabber.defaultCommand = VariableElbow(Elbow.FRONT_DEFAULT, front.grabber.elbow)
//		front.grabber.defaultCommand = VariableWrist(Wrist.FRONT_DEFAULT, front.grabber.wrist)
	}

	override fun cycle() {
	}
}