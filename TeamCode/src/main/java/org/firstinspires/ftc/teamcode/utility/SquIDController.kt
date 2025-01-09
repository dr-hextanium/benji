package org.firstinspires.ftc.teamcode.utility

import com.arcrobotics.ftclib.controller.PIDFController
import kotlin.math.abs
import kotlin.math.sign
import kotlin.math.sqrt

class SquIDController(
    kP: Double,
    kI: Double,
    kD: Double,
    kF: Double
) : PIDFController(kP, kI, kD, kF) {
    constructor(coefficients: DoubleArray) : this(
        coefficients[0],
        coefficients[1],
        coefficients[2],
        coefficients[3]
    )

    override fun calculate(pv: Double, error: Double): Double {
        val raw = super.calculate(pv, error)

        return sqrt(abs(raw)) * raw.sign
    }
}