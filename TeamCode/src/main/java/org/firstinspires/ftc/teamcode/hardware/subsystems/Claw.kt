package org.firstinspires.ftc.teamcode.hardware.subsystems

import com.qualcomm.robotcore.hardware.Servo

// open and close
class Claw(override val servo: Servo) : ISubsystem, IPositionable {
	override var position = 0.0

	override fun reset() = write()

	override fun read() { }

	override fun update() { }

	override fun write() { servo.position = position }

	fun open() { position = State.OPEN.position }
	fun close() { position = State.CLOSED.position }

	enum class State(val position: Double) {
		OPEN(0.0),
		CLOSED(0.0)
	}
}