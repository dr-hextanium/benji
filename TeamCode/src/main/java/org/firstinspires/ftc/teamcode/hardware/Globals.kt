package org.firstinspires.ftc.teamcode.hardware

import com.acmerobotics.roadrunner.DisplacementProfile
import com.acmerobotics.roadrunner.forwardProfile
import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.utility.geometry.Pose2D
import org.firstinspires.ftc.teamcode.utility.geometry.Vector2D.Extensions.left_stick_vector

/**
 * Global configuration for all robot state.
 */
object Globals {
    const val SERVO_PROFILE_LOOKAHEAD = 0.005
    const val DRIVE_MOTOR_THRESHOLD = 0.005

    object Bounds {
        object Front {
            val twist = Bound(0.330, 0.890)
//            val claw  = Bound(0.027, 0.479)
            val claw = Bound(0.230, 0.479)
//            val elbow = Bound(0.000, 0.800)
//            val elbow = Bound(0.145, 0.800)
            val elbow = Bound(0.160, 0.803)
//            val wrist = Bound(0.000, 0.960)
            val wrist = Bound(0.127, 0.960)

        }

        object Back {
            val twist = Bound(0.137, 0.866)
            val claw  = Bound(0.000, 1.000)
            val elbow = Bound(0.535, 0.825)
//            val wrist = Bound(0.146, 1.00)
            val wrist = Bound(0.146, 0.977)
        }

        class Bound(val lower: Double, val upper: Double)
    }

    object Profiles {
        object Front {
            val elbow = ProfilePair(
                forwardProfile(1.0, 0.1, { 0.3 }, { 0.1 }, 0.001),
                forwardProfile(1.0, 0.1, { 0.3 }, { 0.1 }, 0.001),
            )

            val wrist = ProfilePair(
                forwardProfile(1.0, 0.1, { 0.15 }, { 0.07 }, 0.001),
                forwardProfile(1.0, 0.1, { 0.15 }, { 0.07 }, 0.001),
            )
        }

        class ProfilePair(
            val forward: DisplacementProfile,
            val backward: DisplacementProfile
        ) {
            fun forward(position: Double) = forward[position + SERVO_PROFILE_LOOKAHEAD][0]
            fun backward(position: Double) = 1.0 - backward[position + SERVO_PROFILE_LOOKAHEAD][0]
        }
    }

    fun gamepadAsPose(gamepad: Gamepad): Pose2D {
        val velocity = gamepad.left_stick_vector
        val omega = (gamepad.right_trigger - gamepad.left_trigger).toDouble()

        return Pose2D(velocity, omega)
    }
}