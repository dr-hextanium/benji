package org.firstinspires.ftc.teamcode.opmode

import com.arcrobotics.ftclib.command.CommandScheduler
import com.arcrobotics.ftclib.command.InstantCommand
import com.arcrobotics.ftclib.command.button.GamepadButton
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.command.core.CloseClaw
import org.firstinspires.ftc.teamcode.command.core.ElbowPointsDown
import org.firstinspires.ftc.teamcode.command.core.ElbowToTransfer
import org.firstinspires.ftc.teamcode.command.core.OpenClaw
import org.firstinspires.ftc.teamcode.command.core.WristPointsDown
import org.firstinspires.ftc.teamcode.command.core.WristToTransfer
import org.firstinspires.ftc.teamcode.hardware.Robot

@TeleOp
class CommandsImplementation : BasedOpMode() {
	override fun initialize() {
		GamepadButton(Robot.gamepad1, GamepadKeys.Button.X)
			.whenPressed(
				InstantCommand({
					OpenClaw(Robot.Subsystems.front.grabber)
					telemetry.addLine("pressed open claw")
				})
			)

		GamepadButton(Robot.gamepad1, GamepadKeys.Button.Y)
			.whenPressed(
				InstantCommand({
					CloseClaw(Robot.Subsystems.front.grabber)
					telemetry.addLine("pressed close claw")
				})
			)

		GamepadButton(Robot.gamepad1, GamepadKeys.Button.A).whenPressed(ElbowToTransfer(Robot.Subsystems.front.grabber))
		GamepadButton(Robot.gamepad1, GamepadKeys.Button.B).whenPressed(ElbowPointsDown(Robot.Subsystems.front.grabber))
		GamepadButton(Robot.gamepad1, GamepadKeys.Button.LEFT_BUMPER).whenPressed(WristToTransfer(Robot.Subsystems.front.grabber))
		GamepadButton(Robot.gamepad1, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(WristPointsDown(Robot.Subsystems.front.grabber))
	}

	override fun cycle() {

	}
}