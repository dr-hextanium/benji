package org.firstinspires.ftc.teamcode.hardware.subsystems

import com.qualcomm.robotcore.hardware.Servo

class Elbow(val servo: Servo) : ISubsystem {
	var state = State.DOWN

	override fun reset() = write()

	override fun read() {  }

	override fun update() {  }

	override fun write() { servo.position = state.position }

	enum class State(val position: Double) {
		UP(0.0),
		DOWN(0.0)
	}
}