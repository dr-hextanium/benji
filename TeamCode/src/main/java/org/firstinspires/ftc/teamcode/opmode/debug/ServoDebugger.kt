package org.firstinspires.ftc.teamcode.opmode.debug

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.subsystems.Claw

@TeleOp
class ServoDebugger : OpMode() {
	private val claw  = Robot.Subsystems.back.claw
	private val twist = Robot.Subsystems.back.twist
	private val wrist = Robot.Subsystems.back.wrist
	private val elbow = Robot.Subsystems.back.elbow

	override fun init() {
	}

	override fun loop() {
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