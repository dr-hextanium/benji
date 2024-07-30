package org.firstinspires.ftc.teamcode.hardware.drive

import org.firstinspires.ftc.teamcode.utility.geometry.Pose2D

/**
 * `IDrive` - the interface for all drivetrains.
 * - `IDrive`s should only handle the actual movement of the drivetrain
 * - For autonomous, the actual parent class should take in an `IDrive` as a parameter and then
 *   perform operations for the desired kinematics through the class; it should parent `IDrive`
 * - Thus, positional state is not to be preserved within an `IDrive`
 */
interface IDrive {
    /**
     * `pose.position` is used as the translational velocity
     * `pose.heading` is used as the rotational velocity
     */
    fun move(pose: Pose2D)
}