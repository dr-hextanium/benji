package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.utility.geometry.Pose2D
import org.firstinspires.ftc.teamcode.utility.geometry.Vector2D.Extensions.left_stick_vector

/**
 * Global configuration for all robot state.
 */
object Globals {
    const val DRIVE_MOTOR_THRESHOLD = 0.005

    object Bounds {
        object Front {
            val twist = Bound(0.330, 0.893)
            val claw  = Bound(0.000, 1.000)
        }

        class Bound(val lower: Double, val upper: Double)
    }

    fun gamepadAsPose(gamepad: Gamepad): Pose2D {
        val velocity = gamepad.left_stick_vector
        val omega = (gamepad.right_trigger - gamepad.left_trigger).toDouble()

        return Pose2D(velocity, omega)
    }
}