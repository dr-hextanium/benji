package org.firstinspires.ftc.teamcode.hardware.subsystems

import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.hardware.Globals

class Twist(override val servo: Servo, val bound: Globals.Bounds.Bound, initialPosition: Double = 0.0) : ISubsystem, IPositionable {
	override var position = initialPosition

	override fun reset() {
		bound(bound)
	}

	override fun write() = super.write()

	override fun read() {  }

	override fun update() {  }

	companion object {
		const val FRONT_TRANSFER = 1.0
		const val FRONT_INTAKE = 0.5
		const val BACK_TRANSFER = 0.5
	}
}