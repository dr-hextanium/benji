package org.firstinspires.ftc.teamcode.utility.geometry

import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.hardware.Globals

data class Pose2D(val position: Vector2D, val heading: Double) {
    constructor(x: Double, y: Double, heading: Double) : this(Vector2D(x, y), heading)

    val x = position.x
    val y = position.y

    object Extensions {
        val Gamepad.pose
            get() = Globals.gamepadAsPose(this)
    }

    override fun toString() = "Pose2D(x = $x, y = $y, heading = $heading)"
}