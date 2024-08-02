package org.firstinspires.ftc.teamcode.utility

class PIDFController(
    private var kP: Double,
    private var kI: Double,
    private var kD: Double,
    private var kF: Double
) {
    private var before = System.nanoTime()

    private var target = 0.0
    private var lastError = 0.0
    private var integral = 0.0

    fun coeffs(p: Double = kP, i: Double = kI, d: Double = kD, f: Double = kF): PIDFController {
        kP = p
        kI = i
        kD = d
        kF = f

        return this
    }

    fun reset(): PIDFController {
        lastError = 0.0
        integral = 0.0
        before = System.nanoTime()

        return this
    }

    fun target(setpoint: Double): PIDFController {
        this.target = setpoint;
        reset()

        return this
    }

    fun calculate(measured: Double): Double {
        val now = System.nanoTime()
        val error = target - measured

        val dt = (now - before) / 1e9 // nanoseconds to seconds
        val de = (error - lastError)

        integral += error * dt
        lastError = error
        before = now

        return (kP * error) + (kI * integral) + (kD * de / dt) + kF
    }
}
