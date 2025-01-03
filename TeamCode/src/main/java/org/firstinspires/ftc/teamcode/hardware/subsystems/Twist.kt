package org.firstinspires.ftc.teamcode.hardware.subsystems

import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.hardware.Globals

class Twist(override val servo: Servo, val bound: Globals.Bounds.Bound) : ISubsystem, IPositionable {
	override var position = 0.0

	override fun reset() {
		bound(bound)
		write()
	}

	override fun write() = super.write()

	override fun read() {  }

	override fun update() {  }

	companion object {
		const val MIDDLE = 0.5
	}
}