package org.firstinspires.ftc.teamcode.opmode.debug

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Servo

@TeleOp
class DirectServoTest : OpMode() {
	val twist by lazy { hardwareMap["backTwist"] as Servo }

	override fun init() {
		twist.direction = Servo.Direction.REVERSE
	}

	override fun loop() {
		if (gamepad1.square) {
			twist.position = 1.0
		} else if (gamepad1.cross) {
			twist.position = 0.0
		}
	}
}