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
		const val TRANSFER = 1.0
		const val DOWN = 0.0
		const val DEFAULT = 0.5
	}
}