package org.firstinspires.ftc.teamcode.hardware.subsystems

import com.arcrobotics.ftclib.controller.PIDController
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.teamcode.hardware.devices.CachingDcMotor
import kotlin.math.abs
import kotlin.math.sign

class Lift(val pinkLift: DcMotorEx, val blackLift: CachingDcMotor, val encoder: DcMotorEx) : IExtendable {
	override var target = 0
	override var position = 0.0

	val state
		get() = State
			.entries
			.map { it to abs(target - it.target) }
			.minBy { it.second }
			.first

	private val controller = PIDController(kP, kI, kD)

	private fun asInches(ticks: Int) = ticks / ticksPerInch

	override fun reset() {
		encoder.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
		encoder.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
		blackLift.direction = DcMotorSimple.Direction.REVERSE
//		encoder.direction = DcMotorSimple.Direction.REVERSE

		position = 0.0
		target = 0

		pinkLift.power = 0.0
		blackLift.power = 0.0

		pinkLift.setCurrentAlert(3.5, CurrentUnit.AMPS)
		blackLift.setCurrentAlert(3.5, CurrentUnit.AMPS)
	}

	override fun read() {  }

	override fun update() {
		position = asInches(encoder.currentPosition)
	}

	override fun write() {
		val pid = controller.calculate(position, target.toDouble())

		val power = if (controller.atSetPoint()) {
			0.0
		} else {
			pid + (kS * sign(pid))
		}

		pinkLift.power = power
		blackLift.power = power
	}

	enum class State(val target: Int) {
		ZERO(Lift.ZERO),
		HIGH_BASKET(Lift.HIGH_BASKET),
		HIGH_BAR(Lift.HIGH_BAR)
	}

	companion object {
		const val kP = 0.45
		const val kI = 0.01
		const val kD = 0.0
		const val kS = 0.1

		const val ticksPerInch = 4670.0

		const val HIGH_BASKET = 31 // 32
		const val HIGH_BAR = 15 // 20
		const val ZERO = 0
		const val FORCIBLY_ZERO = -2
	}
}