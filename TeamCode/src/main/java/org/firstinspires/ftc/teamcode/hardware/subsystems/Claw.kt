package org.firstinspires.ftc.teamcode.hardware.subsystems

import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.hardware.Globals

class Claw(override val servo: Servo, val bound: Globals.Bounds.Bound) : ISubsystem, IPositionable {
	override var position = 0.0

	override fun reset() {
		bound(bound)
	}

	override fun read() { }

	override fun update() { }

	override fun write() = super.write()

	companion object {
		const val OPEN = 0.0
		const val CLOSED = 1.0
	}
}