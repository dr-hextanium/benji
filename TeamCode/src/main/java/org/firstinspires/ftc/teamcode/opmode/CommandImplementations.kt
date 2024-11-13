package org.firstinspires.ftc.teamcode.opmode

import com.arcrobotics.ftclib.command.button.GamepadButton
import com.arcrobotics.ftclib.gamepad.GamepadEx
import com.arcrobotics.ftclib.gamepad.GamepadKeys.Button
import org.firstinspires.ftc.teamcode.command.core.CloseClaw
import org.firstinspires.ftc.teamcode.command.core.ElbowPointsDown
import org.firstinspires.ftc.teamcode.command.core.ElbowToTransfer
import org.firstinspires.ftc.teamcode.command.core.OpenClaw
import org.firstinspires.ftc.teamcode.command.core.WristPointsDown
import org.firstinspires.ftc.teamcode.command.core.WristToTransfer
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems.GrabberSet
import org.firstinspires.ftc.teamcode.hardware.subsystems.Grabber

class CommandImplementations : BasedOpMode() {
	lateinit var gamepad: GamepadEx
	lateinit var grabber: Grabber
	override fun initialize() {
		grabber = Grabber(GrabberSet().claw, GrabberSet().twist, GrabberSet().wrist, GrabberSet().elbow)
		this.gamepad = GamepadEx(gamepad1)

		GamepadButton(gamepad, Button.X).whenPressed(OpenClaw(grabber))
		GamepadButton(gamepad, Button.Y).whenPressed(CloseClaw(grabber))
		GamepadButton(gamepad, Button.A).whenPressed(ElbowToTransfer(grabber))
		GamepadButton(gamepad, Button.B).whenPressed(ElbowPointsDown(grabber))
		GamepadButton(gamepad, Button.LEFT_BUMPER).whenPressed(WristToTransfer(grabber))
		GamepadButton(gamepad, Button.RIGHT_BUMPER).whenPressed(WristPointsDown(grabber))
	}

	override fun cycle() {

	}
}