package org.firstinspires.ftc.teamcode.hardware.drive

import com.arcrobotics.ftclib.geometry.Pose2d

fun interface Localizer {
    fun getPose(): Pose2d
}