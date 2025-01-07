package org.firstinspires.ftc.teamcode.opmode.auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.auto.localization.Pose
import org.firstinspires.ftc.teamcode.auto.pathGeneration.BezierCurve
import org.firstinspires.ftc.teamcode.auto.pathGeneration.Point
import kotlin.math.PI

@Autonomous
class Bucket : AutoOpMode(start) {
    // to bucket
    override fun buildPaths() {
        val path = follower.pathBuilder()
            .addPath(
                BezierCurve(
                    Point(8.250, 115.000, Point.CARTESIAN),
                    Point(20.644, 118.449, Point.CARTESIAN),
                    Point(15.229, 128.263, Point.CARTESIAN)
                )
            )
            .setLinearHeadingInterpolation(0.0, -PI / 4.0)
            .build()

        follower.setMaxPower(1.0)
        follower.followPath(path)
        follower.telemetryDebug(telemetry)
    }

    override fun initialize() {}

    override fun cycle() {}

    companion object {
        val start = Pose(8.250, 115.000, 0.0)
    }
}