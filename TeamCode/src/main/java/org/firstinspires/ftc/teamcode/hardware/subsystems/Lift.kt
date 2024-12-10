package org.firstinspires.ftc.teamcode.hardware.subsystems

import com.arcrobotics.ftclib.controller.PIDController
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.opmode.debug.ExtendoTuner
import org.firstinspires.ftc.teamcode.opmode.debug.PIDFTuner
import kotlin.math.sign

class Lift(val pinkLift: DcMotorEx, val blackLift: DcMotorEx, val encoder: DcMotorEx) : ISubsystem {
	var target = 0

	val position: Int
		get() = asInches(encoder.currentPosition).toInt()

	private val controller = PIDController(kP, kI, kD)

	private fun asInches(ticks: Int) = ticks.toDouble() / ticksPerInch

	override fun reset() {
		encoder.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
		blackLift.direction = DcMotorSimple.Direction.REVERSE
		encoder.direction = DcMotorSimple.Direction.REVERSE

		pinkLift.power = 0.0
		blackLift.power = 0.0
	}

	override fun read() {  }

	override fun update() {  }

	override fun write() {
		val pid = controller.calculate(position.toDouble(), target.toDouble())

		val power = pid + (kS * sign(pid))

		pinkLift.power = power
		blackLift.power = power
	}

	companion object {
		const val kP = 0.4
		const val kI = 0.0
		const val kD = 0.0
		const val kS = 0.09

		const val ticksPerInch: Int = 4670

		const val HIGH_BASKET = 32
		const val HIGH_BAR = 20
		const val ZERO = 0
	}
}