package org.firstinspires.ftc.teamcode.utility.geometry

import kotlin.math.PI

/**
 * Various utilities for working with angles.
 */
object Angle {
    const val TAU = PI * 2

    /**
     * Returns [angle] clamped to `[0, 2π]`.
     *
     * @param angle angle measure in radians
     */
    @JvmStatic
    fun norm(angle: Double): Double {
        var modifiedAngle = angle % TAU

        modifiedAngle = (modifiedAngle + TAU) % TAU

        return modifiedAngle
    }

    /**
     * Returns [angleDelta] clamped to `[-π, π]`.
     *
     * @param angleDelta angle delta in radians
     */
    @JvmStatic
    fun normDelta(angleDelta: Double): Double {
        var modifiedAngleDelta = norm(angleDelta)

        if (modifiedAngleDelta > PI) {
            modifiedAngleDelta -= TAU
        }

        return modifiedAngleDelta
    }
}