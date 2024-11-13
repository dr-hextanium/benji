package org.firstinspires.ftc.teamcode.hardware.subsystems

import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.hardware.Globals

interface IPositionable {
	var position: Double
	val servo: Servo

	fun bound(lower: Double, upper: Double) {
		servo.scaleRange(lower, upper)
	}
	fun bound(bound: Globals.Bounds.Bound) = bound(bound.lower, bound.upper)

	fun write() { servo.position = position }
}