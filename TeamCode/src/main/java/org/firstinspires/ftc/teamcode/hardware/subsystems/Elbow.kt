package org.firstinspires.ftc.teamcode.hardware.subsystems

import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.hardware.Globals

class Elbow(override val servo: Servo, val bound: Globals.Bounds.Bound) : ISubsystem, IPositionable {
	override var position = 0.0

	override fun reset() {
		bound(bound)
		write()
	}

	override fun read() { }

	override fun update() { }

	override fun write() = super.write()

	companion object {
		const val FRONT_TO_TRANSFER = 0.914 //0.958 //1.0
		const val FRONT_DOWN = 0.0 //0.05
		const val FRONT_INTERMEDIATE = 0.205
		const val FRONT_DEFAULT = 0.850 //0.850

		const val BACK_TO_TRANSFER = 0.029
		const val BACK_TO_DEPOSIT = 1.0
//		const val BACK_DEFAULT = 0.5
	}
}