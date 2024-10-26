package org.firstinspires.ftc.teamcode.opmode.debug

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.subsystems.IPositionable
import org.firstinspires.ftc.teamcode.opmode.BasedOpMode
import kotlin.math.sin

@TeleOp(group = "Debug")
class ServoRangeFinder : BasedOpMode() {
	var start = 0.0
	var end = 1.0

	val subsystem: IPositionable by lazy { Robot.Subsystems.front.claw }

	override fun initialize() {}

	override fun cycle() {
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
			gamepad1.square -> 1.0
			gamepad1.circle -> 0.0
			gamepad1.triangle -> 0.5
			else -> sin(runtime) / 2.0 + 0.5
		}

		telemetry.addData("position", subsystem.position)
		telemetry.addData("start", start)
		telemetry.addData("end", end)
	}
}