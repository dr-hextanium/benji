package org.firstinspires.ftc.teamcode.utility

class PIDFController(
    private val kP: Double,
    private val kI: Double,
    private val kD: Double,
    private val kF: Double
) {
    private var before = System.nanoTime()

    private var target = 0.0
    private var lastError = 0.0
    private var integral = 0.0

    fun reset() {
        lastError = 0.0
        integral = 0.0
        before = System.nanoTime()
    }

    fun target(setpoint: Double) { this.target = setpoint; reset() }

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
