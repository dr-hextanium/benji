package org.firstinspires.ftc.teamcode.opmode.debug

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Servo

@TeleOp
class ServoDebugger : OpMode() {
	private val claw by lazy { hardwareMap["backClaw"] as Servo }
	private val twist by lazy { hardwareMap["frontTwist"] as Servo }
	private val wrist by lazy { hardwareMap["backWrist"] as Servo }
	private val elbow by lazy { hardwareMap["backElbow"] as Servo }

	override fun init() {
		claw
		twist
		wrist
		elbow
	}

	override fun loop() {
		if(gamepad1.cross) {
			claw.position = 1.0
			telemetry.addLine("claw")
		}
		else if(gamepad1.square) {
			twist.position = 1.0
			telemetry.addLine("front twist")
		}
		else if(gamepad1.circle) {
			wrist.position = 1.0
			telemetry.addLine("wrist")
		}
		else if(gamepad1.triangle) {
			elbow.position = 1.0
			telemetry.addLine("elbow")
		}
	}
}