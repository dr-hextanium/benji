package org.firstinspires.ftc.teamcode.opmode.debug

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.subsystems.IPositionable
import org.firstinspires.ftc.teamcode.opmode.BasedOpMode
import org.firstinspires.ftc.teamcode.hardware.Globals.Bounds.Front


@TeleOp(group = "Debug")
class ServoPositionFinder : BasedOpMode() {
	private var elbowPosition = 0.5
	private var wristPosition = 0.5

	private val elbow: IPositionable by lazy { Robot.Subsystems.back.elbow }
	private val wrist: IPositionable by lazy { Robot.Subsystems.back.wrist }


	override fun initialize() {
		//TODO: Do back as well
		elbow.bound(Front.elbow)
		wrist.bound(Front.wrist)
	}

	override fun cycle() {
		elbowPosition += when {
			gamepad1.dpad_up -> 0.001
			gamepad1.dpad_down -> -0.001
			else -> 0.0
		}

		wristPosition += when {
			gamepad1.dpad_right -> 0.001
			gamepad1.dpad_left -> -0.001
			else -> 0.0
		}

		elbow.position = elbowPosition
		wrist.position = wristPosition

		telemetry.addData("elbow position", elbow.position)
		telemetry.addData("wrist position", wrist.position)
	}
}