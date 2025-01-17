package org.firstinspires.ftc.teamcode.hardware.subsystems

import com.arcrobotics.ftclib.controller.PIDController
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.teamcode.opmode.debug.ExtendoTuner.Companion.f
import kotlin.math.abs
import kotlin.math.sign

class Extendo(val extendo: DcMotorEx) : IExtendable {
	override var position = 0.0
	override var target = 0

	val state
		get() = State
			.entries
			.map { it to abs(target - it.target) }
			.minBy { it.second }
			.first

	var power = 0.0

	val controller = PIDController(kP, kI, kD)

	private fun asInches(ticks: Int) = ticks / ticksPerInch

	override fun reset() {
		extendo.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
		extendo.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
		extendo.power = 0.0
	}

	override fun read() {
		position = asInches(extendo.currentPosition)
	}

	override fun update() {
		power = if (abs(target - position) <= TOLERANCE) {
			0.0
		} else {
			val pid = controller.calculate(-position, target.toDouble())

			(pid + f * (target - (-position)).sign).coerceIn(-MAX_POWER, MAX_POWER)
		}
	}

	override fun write() {
		extendo.power = power
	}

	enum class State(val target: Int) {
		TRANSFERING(TO_TRANSFER),
		INTAKING(TO_INTAKE)
	}

	companion object {
		const val TO_TRANSFER = -5
		const val TO_INTAKE = 17

		const val kP = 0.07 //0.15 more recent //0.6
		const val kI = 0.0 //0.0
		const val kD = 0.0 //0.0
		const val kS = 0

		const val ticksPerInch = 220.0

		const val MAX_POWER = 0.8
		const val TOLERANCE = 0.5
	}
}