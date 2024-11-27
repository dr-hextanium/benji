package org.firstinspires.ftc.teamcode.opmode.debug

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor

@TeleOp
class OtherMotorDebugger : OpMode() {
	val extendo by lazy {
		hardwareMap["extendo"] as DcMotor
	}
	val pinkLift by lazy {
		hardwareMap["pinkLift"] as DcMotor
	}
	val blackLift by lazy {
		hardwareMap["blackLift"] as DcMotor
	}
	override fun init() {
	}

	override fun loop() {
		if(gamepad1.cross) {
			//blacklift
			extendo.power = 0.5
		} else if(gamepad1.square) {
			pinkLift.power = 0.5
		} else if(gamepad1.circle) {
			//extendo
			blackLift.power = 0.5
		} else {
			extendo.power = 0.0
			pinkLift.power = 0.0
			blackLift.power = 0.0
		}
	}
}