package org.firstinspires.ftc.teamcode.hardware.devices

import com.qualcomm.robotcore.hardware.AnalogInput
import org.firstinspires.ftc.teamcode.utility.geometry.Angle
import org.firstinspires.ftc.teamcode.utility.geometry.Angle.TAU

/**
 * Adapted from KookyBotz, translated from Java to Kotlin.
 */
class AbsoluteAnalogEncoder(val encoder: AnalogInput, private val range: Double = DEFAULT_RANGE) {
    private var offset = 0.0
    var inverted = false
        private set

    fun zero(offset: Double): AbsoluteAnalogEncoder {
        this.offset = offset
        return this
    }

    fun invert(invert: Boolean): AbsoluteAnalogEncoder {
        inverted = invert
        return this
    }

    val position: Double
        get() {
            val raw = if (!inverted) {
                1 - voltage / range
            } else {
                voltage / range
            }

            return Angle.norm(raw * TAU - offset)
        }

    val voltage: Double
        get() = encoder.voltage

    companion object {
        const val DEFAULT_RANGE = 3.3
    }
}