package org.firstinspires.ftc.teamcode.opmode.auto

import com.pedropathing.pathgen.BezierLine
import com.pedropathing.pathgen.PathBuilder
import com.pedropathing.pathgen.Point

class Yeet {
    init {
        val builder = PathBuilder()

        builder
            .addPath( // Line 1
                BezierLine(
                    Point(8.500, 65.000, Point.CARTESIAN),
                    Point(39.000, 65.000, Point.CARTESIAN)
                )
            )
            .setLinearHeadingInterpolation(Math.toRadians(-180.0), Math.toRadians(-180.0))
            .addPath( // Line 2
                BezierLine(
                    Point(39.000, 65.000, Point.CARTESIAN),
                    Point(20.000, 65.000, Point.CARTESIAN)
                )
            )
            .setLinearHeadingInterpolation(Math.toRadians(-180.0), Math.toRadians(-45.0))
            .addPath( // Line 3
                BezierLine(
                    Point(20.000, 65.000, Point.CARTESIAN),
                    Point(65.000, 24.000, Point.CARTESIAN)
                )
            )
            .setLinearHeadingInterpolation(Math.toRadians(-45.0), Math.toRadians(0.0))
            .addPath( // Line 4
                BezierLine(
                    Point(65.000, 24.000, Point.CARTESIAN),
                    Point(19.000, 24.000, Point.CARTESIAN)
                )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0.0), Math.toRadians(0.0))
            .addPath( // Line 5
                BezierLine(
                    Point(19.000, 24.000, Point.CARTESIAN),
                    Point(60.000, 24.000, Point.CARTESIAN)
                )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0.0), Math.toRadians(0.0))
            .addPath( // Line 6
                BezierLine(
                    Point(60.000, 24.000, Point.CARTESIAN),
                    Point(60.000, 12.000, Point.CARTESIAN)
                )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0.0), Math.toRadians(0.0))
            .addPath( // Line 7
                BezierLine(
                    Point(60.000, 12.000, Point.CARTESIAN),
                    Point(19.000, 12.000, Point.CARTESIAN)
                )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0.0), Math.toRadians(0.0))
            .addPath( // Line 8
                BezierLine(
                    Point(19.000, 12.000, Point.CARTESIAN),
                    Point(25.000, 12.000, Point.CARTESIAN)
                )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0.0), Math.toRadians(0.0))
            .addPath( // Line 9
                BezierLine(
                    Point(25.000, 12.000, Point.CARTESIAN),
                    Point(8.000, 12.000, Point.CARTESIAN)
                )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0.0), Math.toRadians(0.0))
            .addPath( // Line 10
                BezierLine(
                    Point(8.000, 12.000, Point.CARTESIAN),
                    Point(39.000, 75.000, Point.CARTESIAN)
                )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0.0), Math.toRadians(-180.0))
            .addPath( // Line 11
                BezierLine(
                    Point(39.000, 75.000, Point.CARTESIAN),
                    Point(8.500, 35.000, Point.CARTESIAN)
                )
            )
            .setLinearHeadingInterpolation(Math.toRadians(-180.0), Math.toRadians(0.0))
            .addPath( // Line 12
                BezierLine(
                    Point(8.500, 35.000, Point.CARTESIAN),
                    Point(39.000, 70.000, Point.CARTESIAN)
                )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0.0), Math.toRadians(-180.0))
    }
}
