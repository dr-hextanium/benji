package org.firstinspires.ftc.teamcode.opmode.debug

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.subsystems.IPositionable
import org.firstinspires.ftc.teamcode.opmode.BasedOpMode

@TeleOp(group = "Debug")
class ServoRangeFinder : BasedOpMode() {
	val lookahead = 0.005
	var start = 0.0
	var end = 0.5

	val subsystem: IPositionable by lazy { Robot.Subsystems.front.twist }

	override fun initialize() {
//		CommandScheduler.getInstance().schedule(
//			VariableWrist(Wrist.FRONT_TO_TRANSFER, Robot.Subsystems.front.grabber.wrist),
//			VariableElbow(Elbow.FRONT_TO_TRANSFER, Robot.Subsystems.front.grabber.elbow),
//			VariableElbow(Elbow.BACK_TO_TRANSFER, Robot.Subsystems.back.grabber.elbow),
//			VariableTwist(Twist.MIDDLE, Robot.Subsystems.back.grabber),
//			VariableTwist(Twist.MIDDLE, Robot.Subsystems.front.grabber)
//		)
//		CommandScheduler.getInstance().run()
	}

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