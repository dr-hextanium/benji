package org.firstinspires.ftc.teamcode.opmode.debug

import com.arcrobotics.ftclib.command.ParallelCommandGroup
import com.arcrobotics.ftclib.command.button.GamepadButton
import com.arcrobotics.ftclib.command.button.Trigger
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.arcrobotics.ftclib.gamepad.TriggerReader
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.command.core.ElbowToTransfer
import org.firstinspires.ftc.teamcode.command.core.VariableElbow
import org.firstinspires.ftc.teamcode.command.core.VariableTwist
import org.firstinspires.ftc.teamcode.command.core.VariableWrist
import org.firstinspires.ftc.teamcode.command.core.WristToTransfer
import org.firstinspires.ftc.teamcode.hardware.Globals
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow
import org.firstinspires.ftc.teamcode.hardware.subsystems.IPositionable
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist
import org.firstinspires.ftc.teamcode.opmode.BasedOpMode

@TeleOp
class TwistTest : BasedOpMode() {
	override fun initialize() {
		Trigger { TriggerReader(Robot.gamepad1, GamepadKeys.Trigger.RIGHT_TRIGGER).isDown }
			.whileActiveContinuous(VariableTwist({ Robot.Subsystems.front.twist.position + 0.03 }, Robot.Subsystems.front.grabber, 10))

		Trigger { TriggerReader(Robot.gamepad1, GamepadKeys.Trigger.LEFT_TRIGGER).isDown }
			.whileActiveContinuous(VariableTwist({ Robot.Subsystems.front.twist.position - 0.03 }, Robot.Subsystems.front.grabber, 10))

//		GamepadButton(Robot.gamepad1, GamepadKeys.Button.DPAD_RIGHT)
//			.whenPressed(VariableTwist({ Robot.Subsystems.front.twist.position + 0.05 }, Robot.Subsystems.front.grabber))
//
//		GamepadButton(Robot.gamepad1, GamepadKeys.Button.DPAD_LEFT)
//			.whenPressed(VariableTwist({ Robot.Subsystems.front.twist.position - 0.05 }, Robot.Subsystems.front.grabber))
	}

	override fun cycle() { }

}