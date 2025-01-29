package org.firstinspires.ftc.teamcode.hardware.subsystems

import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.hardware.Globals

class Wrist(override val servo: Servo, val bound: Globals.Bounds.Bound) : ISubsystem, IPositionable {
	override var position = 0.0

	override fun reset() {
		bound(bound)
	}

	override fun read() {}

	override fun update() {}

	override fun write() = super.write()

	companion object {
		const val FRONT_TO_TRANSFER = 1.0 //0.964 //1.0
		const val FRONT_DEFAULT = 0.320 // 0.320
		const val FRONT_DOWN = 0.0 // 0.0
		const val FRONT_INTERMEDIATE = 0.0 //0.051

		const val BACK_TO_TRANSFER = 0.157 //0.151 //0.12 //0.034 //0.0
		const val BACK_TO_DEPOSIT = 0.959 // 1.0
		const val BACK_DEFAULT = 0.610 // 0.578
		const val BACK_SPECIMEN_DEPOSIT = 0.95
		const val BACK_SPEC_GRAB = 0.825
	}
}