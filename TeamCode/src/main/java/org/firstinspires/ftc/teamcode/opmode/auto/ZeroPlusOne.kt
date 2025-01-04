package org.firstinspires.ftc.teamcode.opmode.auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.auto.localization.Pose
import org.firstinspires.ftc.teamcode.auto.pathGeneration.BezierCurve
import org.firstinspires.ftc.teamcode.auto.pathGeneration.BezierLine
import org.firstinspires.ftc.teamcode.auto.pathGeneration.Point
import kotlin.math.PI

@Autonomous
class ZeroPlusOne : AutoOpMode(start) {
    override fun buildPaths() {
        val path = follower.pathBuilder()
            .addPath(
                // Line 1
                BezierCurve(
                    Point(8.250, 115.000, Point.CARTESIAN),
                    Point(21.000, 118.000, Point.CARTESIAN),
                    Point(15.000, 128.000, Point.CARTESIAN)
                )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0.0), Math.toRadians(-45.0))
            .addPath(
                // Line 2
                BezierLine(
                    Point(15.000, 128.000, Point.CARTESIAN),
                    Point(24.250, 125.000, Point.CARTESIAN)
                )
            )
            .setLinearHeadingInterpolation(Math.toRadians(-45.0), Math.toRadians(-10.0))
            .addPath(
                // Line 3
                BezierLine(
                    Point(24.250, 125.000, Point.CARTESIAN),
                    Point(15.000, 128.000, Point.CARTESIAN)
                )
            )
            .setLinearHeadingInterpolation(Math.toRadians(-10.0), Math.toRadians(-45.0))
            .build()


        follower.followPath(path)
        follower.telemetryDebug(telemetry)
    }

    override fun initialize() { }

    override fun cycle() { }

    companion object {
        val start = Pose(8.250, 115.000, 0.0)
    }
}