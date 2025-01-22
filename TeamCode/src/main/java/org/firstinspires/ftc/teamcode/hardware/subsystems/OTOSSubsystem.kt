package org.firstinspires.ftc.teamcode.hardware.subsystems

import com.acmerobotics.roadrunner.ftc.SparkFunOTOSCorrected
import com.acmerobotics.dashboard.canvas.Canvas
import com.arcrobotics.ftclib.geometry.Pose2d
import com.arcrobotics.ftclib.geometry.Rotation2d
import com.arcrobotics.ftclib.geometry.Vector2d
import com.qualcomm.hardware.sparkfun.SparkFunOTOS
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
//import org.firstinspires.ftc.teamcode.hardware.devices.SparkFunOTOSCorrected
import org.firstinspires.ftc.teamcode.hardware.drive.Localizer

class OTOSSubsystem(val sensor: SparkFunOTOSCorrected) : Localizer, ISubsystem {
    val calibrationProgress: Int
        get() = sensor.imuCalibrationProgress

    val isDoneCalibration: Boolean
        get() = calibrationProgress < 1

    private var pose = SparkFunOTOS.Pose2D()

    override fun getPose(): Pose2d {
        return Pose2d(
            pose.x, pose.y, Rotation2d.fromDegrees(pose.h)
        )
    }

    fun getDegrees() = pose.h

    fun setPosition(x: Double, y: Double) {
        sensor.position = SparkFunOTOS.Pose2D(x, y, pose.h)
    }

    override fun reset() {
        sensor.linearUnit = DistanceUnit.INCH
        sensor.angularUnit = AngleUnit.DEGREES

        sensor.offset = SparkFunOTOS.Pose2D(0.0, 0.0, 2 * Math.PI - Math.PI / 2)
        sensor.setLinearScalar(1.035598)
        sensor.setAngularScalar(0.98603)

        sensor.calibrateImu(255, true)

        sensor.resetTracking()
    }

    override fun read() { pose = sensor.position }

    override fun update() { }

    override fun write() { }

    fun drawRobot(c: Canvas, t: Pose2d) {
        val ROBOT_RADIUS = 7.0

        c.setStrokeWidth(1)
        c.strokeCircle(t.x, t.y, ROBOT_RADIUS)

        val halfv = Vector2d(t.rotation.cos, t.rotation.sin).times(0.5 * ROBOT_RADIUS)
        val p1 = Vector2d(t.x, t.y).plus(halfv)
        val p2 = p1.plus(halfv)
        c.strokeLine(p1.x, p1.y, p2.x, p2.y)
    }

    /**
     * Warning - will completely break position!!
     */
    fun resetPose() {
        sensor.position = SparkFunOTOS.Pose2D(0.0, 0.0, 0.0)
    }
}