package org.firstinspires.ftc.teamcode.utility.geometry

import com.qualcomm.robotcore.hardware.Gamepad
import kotlin.math.sin
import kotlin.math.cos
import kotlin.math.acos
import kotlin.math.atan2
import kotlin.math.hypot

data class Vector2D(val x: Double, val y: Double) {
    val magnitude = hypot(x, y)
    val theta = atan2(y, x)

    constructor(x: Float, y: Float) : this(x.toDouble(), y.toDouble())

    operator fun plus(other: Vector2D) = Vector2D(x + other.x, y + other.y)
    operator fun times(other: Vector2D) = Vector2D(x * other.x, y * other.y)
    operator fun times(n: Double) = Vector2D(x * n, y * n)
    operator fun times(n: Int) = times(n.toDouble())

    operator fun minus(other: Vector2D) = plus(-other)
    operator fun unaryMinus() = Vector2D(-x, -y)

    override fun toString() = "Vector2D(x = $x, y = $y, theta = $theta)"

    fun perpendicular() = Vector2D(y, -x)

    fun rotate(theta: Double): Vector2D {
        val x = x * cos(theta) - y * sin(theta)
        val y = x * sin(theta) + y * cos(theta)

        return Vector2D(x, y)
    }

    fun angleBetween(other: Vector2D): Double {
        return acos(((x * other.x) + (y * other.y)) / (magnitude * other.magnitude))
    }

    object Extensions {
        val Gamepad.left_stick_vector
            get() = Vector2D(this.left_stick_x, this.left_stick_y)
        val Gamepad.right_stick_vector
            get() = Vector2D(this.left_stick_x, this.left_stick_y)
    }

    companion object {
        fun polar(magnitude: Double, theta: Double) =
            Vector2D(magnitude * cos(theta), magnitude * sin(theta))
    }
}