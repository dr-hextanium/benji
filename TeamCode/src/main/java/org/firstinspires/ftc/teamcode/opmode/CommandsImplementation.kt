package org.firstinspires.ftc.teamcode.opmode

import com.arcrobotics.ftclib.command.Command
import com.arcrobotics.ftclib.command.CommandScheduler
import com.arcrobotics.ftclib.command.InstantCommand
import com.arcrobotics.ftclib.command.ParallelCommandGroup
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.arcrobotics.ftclib.command.button.GamepadButton
import com.arcrobotics.ftclib.command.button.Trigger
import com.arcrobotics.ftclib.gamepad.GamepadEx
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.arcrobotics.ftclib.gamepad.TriggerReader
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.command.core.CloseClaw
import org.firstinspires.ftc.teamcode.command.core.ElbowPointsDown
import org.firstinspires.ftc.teamcode.command.core.ElbowToDefault
import org.firstinspires.ftc.teamcode.command.core.ElbowToTransfer
import org.firstinspires.ftc.teamcode.command.core.OpenClaw
import org.firstinspires.ftc.teamcode.command.core.WristPointsDown
import org.firstinspires.ftc.teamcode.command.core.WristToDefault
import org.firstinspires.ftc.teamcode.command.core.WristToTransfer
import org.firstinspires.ftc.teamcode.hardware.Robot

@TeleOp
class CommandsImplementation : BasedOpMode() {
	override fun initialize() {
		GamepadButton(Robot.gamepad1, GamepadKeys.Button.X)
			.whenPressed(OpenClaw(Robot.Subsystems.front.grabber))
		GamepadButton(Robot.gamepad1, GamepadKeys.Button.Y)
			.whenPressed(CloseClaw(Robot.Subsystems.front.grabber))

		GamepadButton(Robot.gamepad1, GamepadKeys.Button.A).whenPressed(ElbowToTransfer(Robot.Subsystems.front.grabber))
		GamepadButton(Robot.gamepad1, GamepadKeys.Button.B).whenPressed(ElbowPointsDown(Robot.Subsystems.front.grabber))
		GamepadButton(Robot.gamepad1, GamepadKeys.Button.LEFT_BUMPER).whenPressed(WristToTransfer(Robot.Subsystems.front.grabber))
		GamepadButton(Robot.gamepad1, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(WristPointsDown(Robot.Subsystems.front.grabber))

//		Trigger { TriggerReader(Robot.gamepad1, GamepadKeys.Trigger.RIGHT_TRIGGER).isDown }
//			.whileActiveContinuous(
//				ParallelCommandGroup(
//					ElbowToTransfer(Robot.Subsystems.front.grabber),
//					WristToTransfer(Robot.Subsystems.front.grabber),
//					OpenClaw(Robot.Subsystems.front.grabber)
//				)
//			)
//			.whenInactive(
//				ParallelCommandGroup(
//					ElbowPointsDown(Robot.Subsystems.front.grabber),
//					WristPointsDown(Robot.Subsystems.front.grabber),
//					CloseClaw(Robot.Subsystems.front.grabber)
//				)
//			)
		Trigger { TriggerReader(Robot.gamepad1, GamepadKeys.Trigger.RIGHT_TRIGGER).isDown }
			.whileActiveContinuous(
				ParallelCommandGroup(
					ElbowToTransfer(Robot.Subsystems.front.grabber),
					WristToTransfer(Robot.Subsystems.front.grabber)
				)
			)
			.whenInactive(
				ParallelCommandGroup(
					ElbowToDefault(Robot.Subsystems.front.grabber),
					WristToDefault(Robot.Subsystems.front.grabber)
				)
			)

		Trigger { TriggerReader(Robot.gamepad1, GamepadKeys.Trigger.LEFT_TRIGGER).isDown }
			.whileActiveContinuous(
				SequentialCommandGroup(
					WristPointsDown(Robot.Subsystems.front.grabber),
					ElbowPointsDown(Robot.Subsystems.front.grabber),
					CloseClaw(Robot.Subsystems.front.grabber)
				)
			)
			.whenInactive(
				ParallelCommandGroup(
					ElbowToDefault(Robot.Subsystems.front.grabber),
					WristToDefault(Robot.Subsystems.front.grabber)
				)
			)
	}

	override fun cycle() {
	}
}