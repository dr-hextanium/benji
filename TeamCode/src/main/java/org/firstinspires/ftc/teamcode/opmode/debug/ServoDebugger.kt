package org.firstinspires.ftc.teamcode.opmode.debug

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.subsystems.Claw
import org.firstinspires.ftc.teamcode.opmode.BasedOpMode

@TeleOp
class ServoDebugger : BasedOpMode() {
	private val wrist by lazy { Robot.Subsystems.front.twist }

	override fun initialize() {

	}

	override fun cycle() {
		if(gamepad1.cross) {
			wrist.position = 1.0
			telemetry.addData("position", wrist.position)
		}
		else if(gamepad1.square) {
			wrist.position = 0.0
			telemetry.addData("position", wrist.position)
		}
		else if(gamepad1.circle) {
			wrist.position = 0.5
			telemetry.addData("position", wrist.position)
		}
	}
}