package org.firstinspires.ftc.teamcode.hardware.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx

class Lift(val pinkLift: DcMotorEx, val blackLift: DcMotorEx) : ISubsystem {
	var pinkPosition = 0
	var blackPosition = 0
	var targetPosition = 0

	override fun reset() {
		pinkLift.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
		blackLift.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER

		pinkLift.power = 0.0
		blackLift.power = 0.0

		pinkLift.targetPosition = 0
		blackLift.targetPosition = 0
	}

	override fun read() {
		pinkPosition = pinkLift.currentPosition
		blackPosition = blackLift.currentPosition
	}

	override fun update() {
		pinkLift.targetPosition = targetPosition
		blackLift.targetPosition = targetPosition
	}

	override fun write() {
		pinkLift.mode = DcMotor.RunMode.RUN_TO_POSITION
		blackLift.mode = DcMotor.RunMode.RUN_TO_POSITION

		pinkLift.power = 1.0
		blackLift.power = 1.0
	}
}