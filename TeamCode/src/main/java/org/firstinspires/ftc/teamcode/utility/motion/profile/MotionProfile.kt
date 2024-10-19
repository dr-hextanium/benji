package org.firstinspires.ftc.teamcode.utility.motion.profile

import kotlin.math.pow
import kotlin.math.sqrt

abstract class MotionProfile(
	val goal: Double,
	val constraints: Constraints
) {
	val falling = constraints.falling
	val rising = constraints.rising
	val max = constraints.max

	val time: Double
		get() = Time.accel + Time.cruise + Time.decel

	object Time {
		var accel = 0.0
		var decel = 0.0
		var cruise = 0.0
	}

	object Distance {
		var accel = 0.0
		var decel = 0.0
		var cruise = 0.0
	}

	abstract fun velocityAtDisplacement(displacement: Double): Double

	interface State {
		var accel: Double
		var decel: Double
		var cruise: Double
	}

	class Constraints(
		val max: Double,
		val rising: Double,
		val falling: Double
	)
}