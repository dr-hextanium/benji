package org.firstinspires.ftc.teamcode.hardware.subsystems

import com.acmerobotics.roadrunner.DisplacementProfile
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.teamcode.utility.motion.profile.AsymmetricMotionProfile
import org.firstinspires.ftc.teamcode.utility.motion.profile.Constraints

class Extendo(val extendo: DcMotorEx) : ISubsystem {
	var extendoPosition = 0
	var targetPosition = 0

	val profile = AsymmetricMotionProfile(0.0, 1.0, Constraints(0.0, 0.0, 0.0))

	override fun reset() {
		extendo.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
		extendo.power = 0.0
		extendo.targetPosition = 0
	}

	override fun read() {
		extendoPosition = extendo.currentPosition
	}

	override fun update() {
		extendo.targetPosition = targetPosition
	}

	override fun write() {
		extendo.mode = DcMotor.RunMode.RUN_TO_POSITION
		extendo.power = 1.0
	}
}