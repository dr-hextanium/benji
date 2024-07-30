package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.utility.geometry.Pose2D
import org.firstinspires.ftc.teamcode.utility.geometry.Vector2D.Extensions.left_stick_vector

/**
 * Global configuration for all robot state.
 */
object Globals {
    fun gamepadAsPose(gamepad: Gamepad): Pose2D {
        val velocity = gamepad.left_stick_vector
        val omega = (gamepad.right_trigger - gamepad.left_trigger).toDouble()

        return Pose2D(velocity, omega)
    }
}