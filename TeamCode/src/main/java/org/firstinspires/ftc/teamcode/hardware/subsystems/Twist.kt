package org.firstinspires.ftc.teamcode.hardware.subsystems

import com.qualcomm.robotcore.hardware.Servo

class Twist(override val servo: Servo, val offset: Double) : ISubsystem, IPositionable {
	override var position = 0.0

	override fun reset() = write()

	override fun read() {  }

	override fun update() {  }

	override fun write() {
		servo.position = position + offset
	}
}