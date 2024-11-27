package org.firstinspires.ftc.teamcode.opmode.debug

import com.acmerobotics.roadrunner.backwardProfile
import com.acmerobotics.roadrunner.forwardProfile
import com.arcrobotics.ftclib.command.CommandScheduler
import com.arcrobotics.ftclib.command.button.GamepadButton
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.command.core.ElbowToTransfer
import org.firstinspires.ftc.teamcode.command.core.VariableElbow
import org.firstinspires.ftc.teamcode.command.core.VariableWrist
import org.firstinspires.ftc.teamcode.hardware.Globals
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow
import org.firstinspires.ftc.teamcode.hardware.subsystems.IPositionable
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist
import org.firstinspires.ftc.teamcode.opmode.BasedOpMode
import kotlin.math.sin

@TeleOp(group = "Debug")
class DualServoRangeFinder : BasedOpMode() {
	val lookahead = 0.005
	var start = 0.0
	var end = 0.5

	val subsystem: IPositionable by lazy { Robot.Subsystems.back.wrist }

	override fun initialize() {
		CommandScheduler.getInstance().schedule(
			VariableElbow(Elbow.BACK_TO_TRANSFER, Robot.Subsystems.back.elbow),
			VariableElbow(Elbow.FRONT_TO_TRANSFER, Robot.Subsystems.front.elbow),
			VariableWrist(Wrist.FRONT_TO_TRANSFER, Robot.Subsystems.front.wrist)
		)
	}

	override fun cycle() {
		CommandScheduler.getInstance().run()
		start += when {
			gamepad1.dpad_right -> 0.001
			gamepad1.dpad_left -> -0.001
			else -> 0.0
		}

		end += when {
			gamepad1.dpad_up -> 0.001
			gamepad1.dpad_down -> -0.001
			else -> 0.0
		}

		subsystem.bound(start, end)

		subsystem.position = when {
			gamepad1.right_trigger > 0.0 -> gamepad1.right_trigger.toDouble()

			gamepad1.square -> 0.0
			gamepad1.circle -> 1.0

			else -> 0.5
//			else -> sin(runtime) / 2.0 + 0.5
		}

		telemetry.addData("position", subsystem.position)
		telemetry.addData("start", start)
		telemetry.addData("end", end)
	}
}