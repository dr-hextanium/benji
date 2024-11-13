package org.firstinspires.ftc.teamcode.opmode.debug

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.hardware.Globals
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.subsystems.IPositionable
import org.firstinspires.ftc.teamcode.opmode.BasedOpMode

@TeleOp(group = "Debug")
class SynchronizedServoRangeFinder : BasedOpMode() {
	val a: IPositionable by lazy { Robot.Subsystems.front.wrist }
	val b: IPositionable by lazy { Robot.Subsystems.front.elbow }

	val ap = Globals.Profiles.Front.wrist
	val bp = Globals.Profiles.Front.elbow

	override fun initialize() {}

	override fun cycle() {
		a.bound(Globals.Bounds.Front.wrist)
		b.bound(Globals.Bounds.Front.elbow)

		a.position = when {
			gamepad1.right_trigger > 0f -> gamepad1.right_trigger.toDouble()

			gamepad1.square -> 0.0
			gamepad1.circle -> 1.0

			else -> 0.5
		}

		b.position = when {
			gamepad1.left_trigger > 0f -> gamepad1.left_trigger.toDouble()

			gamepad1.square -> 0.0
			gamepad1.circle -> 1.0

			else -> 0.5
		}

		telemetry.addData("a position", a.position)
		telemetry.addData("b position", b.position)
	}
}