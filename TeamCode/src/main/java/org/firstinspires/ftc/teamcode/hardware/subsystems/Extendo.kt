package org.firstinspires.ftc.teamcode.hardware.subsystems

import com.arcrobotics.ftclib.controller.PIDController
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.teamcode.hardware.subsystems.Lift.Companion
import org.firstinspires.ftc.teamcode.opmode.debug.ExtendoTuner
import org.firstinspires.ftc.teamcode.opmode.debug.ExtendoTuner.Companion.f
import org.firstinspires.ftc.teamcode.utility.motion.profile.AsymmetricMotionProfile
import org.firstinspires.ftc.teamcode.utility.motion.profile.Constraints
import kotlin.math.sign

class Extendo(val extendo: DcMotorEx) : IExtendable {
	override var position = 0
	override var target = 0

	private val controller = PIDController(kP, kI, kD)

	private fun asInches(ticks: Int) = ticks / ticksPerInch

	override fun reset() {
		extendo.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
		extendo.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
		extendo.power = 0.0

//		extendo.setCurrentAlert(3.5, CurrentUnit.AMPS)
//		write()
	}

	override fun read() {  }

	override fun update() {
		position = asInches(extendo.currentPosition)
	}

	override fun write() {
		val pid = controller.calculate(-position.toDouble(), target.toDouble())

		val power = if(controller.atSetPoint()) {
			0.0
		} else {
			pid + f * (target - (-position)).sign
		}

		extendo.power = power
	}

	companion object {
		const val TO_TRANSFER = -2
		const val TO_INTAKE = 17

		const val kP = 0.15 //0.6
		const val kI = 0.1 //0.0
		const val kD = 0.01 //0.0
		const val kS = 0

		const val ticksPerInch = 220
	}
}