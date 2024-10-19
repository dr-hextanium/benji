package org.firstinspires.ftc.teamcode.utility.motion.profile

import kotlin.math.pow
import kotlin.math.sqrt

class TrapezoidalProfile(goal: Double, constraints: Constraints) : MotionProfile(goal, constraints) {
	init {
		Time.accel = max / rising
		Time.decel = max / falling

		Distance.accel = rising * Time.accel.pow(2) / 2.0
		Distance.decel = falling * Time.decel.pow(2) / 2.0

		val acceleratingDistance = Distance.accel + Distance.decel

		if (goal > acceleratingDistance) {
			Distance.cruise = goal - acceleratingDistance
			Time.cruise = Distance.cruise / max
		} else {
			val midpoint = goal / 2.0

			Time.accel = sqrt(2.0 * midpoint / rising)
			Time.decel = sqrt(2.0 * midpoint / falling)

			Distance.accel = midpoint
			Distance.decel = midpoint

			Time.cruise = 0.0
			Distance.cruise = 0.0
		}
	}

	override fun velocityAtDisplacement(displacement: Double): Double {
		return when {
			displacement <= Distance.accel -> sqrt(2 * rising * displacement)

			displacement <= (Distance.accel + Distance.cruise) -> max

			displacement <= goal -> {
				val starts = Distance.accel + Distance.cruise
				val inside = displacement - starts

				sqrt(2.0 * falling * (Distance.decel - inside))
			}

			else -> 0.0
		}
	}
}