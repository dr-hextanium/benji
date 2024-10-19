package org.firstinspires.ftc.teamcode.hardware.subsystems

import com.qualcomm.robotcore.hardware.Servo

class Twist(val servo: Servo, val offset: Double) : ISubsystem {
	var position = offset

	override fun reset() = write()

	override fun read() {  }

	override fun update() {  }

	override fun write() { servo.position = position + offset }
}