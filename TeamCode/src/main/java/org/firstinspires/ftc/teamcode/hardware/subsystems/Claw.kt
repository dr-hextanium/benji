package org.firstinspires.ftc.teamcode.hardware.subsystems

import com.qualcomm.robotcore.hardware.Servo

// open and close
class Claw(val servo: Servo) : ISubsystem {
	var state = State.CLOSED

	override fun reset() = write()

	override fun read() { }

	override fun update() { }

	override fun write() { servo.position = state.position }

	fun open() { state = State.OPEN }
	fun close() { state = State.CLOSED }

	enum class State(val position: Double) {
		OPEN(0.0),
		CLOSED(0.0)
	}
}