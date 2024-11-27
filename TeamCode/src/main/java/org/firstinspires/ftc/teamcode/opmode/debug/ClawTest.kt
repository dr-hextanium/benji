package org.firstinspires.ftc.teamcode.opmode.debug

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Servo

@TeleOp
class DirectServoTest : OpMode() {
	val claw by lazy { hardwareMap["backClaw"] as Servo }

	override fun init() {
		claw.direction = Servo.Direction.REVERSE
	}

	override fun loop() {
		if (gamepad1.square) {
			claw.position = 1.0
		} else if (gamepad1.circle) {
			claw.position = 0.0
		}
	}
}