package org.firstinspires.ftc.teamcode.hardware.subsystems

import com.qualcomm.robotcore.hardware.Servo

class Wrist(val servo: Servo) : ISubsystem {
	var position = 0.0

	override fun reset() {}

	override fun read() {}

	override fun update() {}

	fun to(state: State) { position = state.position }

	override fun write() { servo.position = position }

	enum class State(val position: Double) {
		UP(0.0),
		DOWN(0.0)
	}
}