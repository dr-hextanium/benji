package org.firstinspires.ftc.teamcode.opmode.auto

import com.arcrobotics.ftclib.command.Command
import com.arcrobotics.ftclib.command.CommandScheduler
import com.arcrobotics.ftclib.command.ParallelCommandGroup
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.arcrobotics.ftclib.command.WaitCommand
import com.pedropathing.localization.Pose
import com.pedropathing.pathgen.BezierCurve
import com.pedropathing.pathgen.BezierLine
import com.pedropathing.pathgen.Point
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.command.auto.DepositToBasket
import org.firstinspires.ftc.teamcode.command.auto.PedroPathCommand
import org.firstinspires.ftc.teamcode.command.core.OpenClaw
import org.firstinspires.ftc.teamcode.command.core.VariableElbow
import org.firstinspires.ftc.teamcode.command.core.VariableWrist
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems.back
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems.front
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist
import kotlin.math.PI

@Autonomous
class TwoSpecimen : AutoOpMode(Pose(8.5, 65.0, -PI)) {
    val commands = mutableListOf<Command>()

    override fun paths() {
        val initial = follower.pathBuilder()
            .addPath( // Line 1
                BezierLine(
                    Point(8.500, 65.000, Point.CARTESIAN),
                    Point(39.000, 78.000, Point.CARTESIAN)
                )
            )
            .setLinearHeadingInterpolation(Math.toRadians(-180.0), Math.toRadians(-180.0))
            .setZeroPowerAccelerationMultiplier(0.001)
            .build()

        val initialSetup = follower.pathBuilder()
            .addPath( // Line 2
                BezierCurve(
                    Point(39.000, 78.000, Point.CARTESIAN),
                    Point(21.250, 48.750, Point.CARTESIAN),
                    Point(35.000, 37.000, Point.CARTESIAN),
                    Point(63.000, 33.000, Point.CARTESIAN)
                )
            )
            .setLinearHeadingInterpolation(Math.toRadians(-180.0), Math.toRadians(-180.0))
            .build()
//
//        val initialSpin = builder
//            .addPath( // Line 3
//                BezierLine(
//                    Point(63.000, 33.000, Point.CARTESIAN),
//                    Point(63.000, 23.000, Point.CARTESIAN)
//                )
//            )
//            .setLinearHeadingInterpolation(Math.toRadians(-180.0), Math.toRadians(0.0))
//            .build()
//
//        val firstPush = builder
//            .addPath( // Line 4
//                BezierLine(
//                    Point(63.000, 23.000, Point.CARTESIAN),
//                    Point(16.000, 23.000, Point.CARTESIAN)
//                )
//            )
//            .setLinearHeadingInterpolation(Math.toRadians(0.0), Math.toRadians(0.0))
//            .build()
//
//        val firstReturn = builder
//            .addPath( // Line 5
//                BezierLine(
//                    Point(16.000, 23.000, Point.CARTESIAN),
//                    Point(63.000, 23.000, Point.CARTESIAN)
//                )
//            )
//            .setLinearHeadingInterpolation(Math.toRadians(0.0), Math.toRadians(0.0))
//            .build()
//
//        val secondSetup = builder
//            .addPath( // Line 6
//                BezierLine(
//                    Point(63.000, 23.000, Point.CARTESIAN),
//                    Point(63.000, 16.000, Point.CARTESIAN)
//                )
//            )
//            .setLinearHeadingInterpolation(Math.toRadians(0.0), Math.toRadians(0.0))
//            .build()
//
//        val secondPush = builder
//            .addPath( // Line 7
//                BezierLine(
//                    Point(63.000, 16.000, Point.CARTESIAN),
//                    Point(16.000, 16.000, Point.CARTESIAN)
//                )
//            )
//            .setLinearHeadingInterpolation(Math.toRadians(0.0), Math.toRadians(0.0))
//            .build()
//
//        val firstPickup = builder
//            .addPath( // Line 8
//                BezierCurve(
//                    Point(16.000, 16.000, Point.CARTESIAN),
//                    Point(35.000, 37.000, Point.CARTESIAN),
//                    Point(8.500, 37.000, Point.CARTESIAN)
//                )
//            )
//            .setLinearHeadingInterpolation(Math.toRadians(0.0), Math.toRadians(0.0))
//            .build()
//
//        val firstScore = builder
//            .addPath( // Line 9
//                BezierLine(
//                    Point(8.500, 37.000, Point.CARTESIAN),
//                    Point(41.000, 74.000, Point.CARTESIAN)
//                )
//            )
//            .setLinearHeadingInterpolation(Math.toRadians(0.0), Math.toRadians(-180.0))
//            .build()
//
//        val secondPickup = builder
//            .addPath( // Line 10
//                BezierLine(
//                    Point(41.000, 74.000, Point.CARTESIAN),
//                    Point(8.500, 37.000, Point.CARTESIAN)
//                )
//            )
//            .setLinearHeadingInterpolation(Math.toRadians(-180.0), Math.toRadians(0.0))
//            .build()
//
//        val secondScore = builder
//            .addPath( // Line 11
//                BezierLine(
//                    Point(8.500, 37.000, Point.CARTESIAN),
//                    Point(41.000, 70.000, Point.CARTESIAN)
//                )
//            )
//            .setLinearHeadingInterpolation(Math.toRadians(0.0), Math.toRadians(-180.0))
//            .build()
//
//        val thirdPickup = builder
//            .addPath( // Line 12
//                BezierLine(
//                    Point(41.000, 70.000, Point.CARTESIAN),
//                    Point(8.500, 37.000, Point.CARTESIAN)
//                )
//            )
//            .setLinearHeadingInterpolation(Math.toRadians(-180.0), Math.toRadians(0.0))
//            .build()
//
//        val thirdScore = builder
//            .addPath( // Line 13
//                BezierLine(
//                    Point(8.500, 37.000, Point.CARTESIAN),
//                    Point(41.000, 66.000, Point.CARTESIAN)
//                )
//            )
//            .setLinearHeadingInterpolation(Math.toRadians(0.0), Math.toRadians(-180.0))
//            .build()
//
//        val park = builder
//            .addPath( // Line 14
//                BezierLine(
//                    Point(41.000, 66.000, Point.CARTESIAN),
//                    Point(8.500, 37.000, Point.CARTESIAN)
//                )
//            )
//            .setLinearHeadingInterpolation(Math.toRadians(-180.0), Math.toRadians(0.0))
//            .build()

//        follower.holdPoint(Point(39.000, 78.000))

        commands.add(
            SequentialCommandGroup(
                PedroPathCommand(initial, follower, timer),
            )
        )
    }

    override fun initialize() {
        CommandScheduler.getInstance().schedule(
            VariableWrist(Wrist.BACK_DEFAULT, back.grabber.wrist),
            VariableElbow(Elbow.BACK_TO_DEPOSIT, back.grabber.elbow),
            VariableWrist(Wrist.FRONT_DEFAULT, front.grabber.wrist),
            VariableElbow(Elbow.FRONT_DEFAULT, front.grabber.elbow)
        )
    }

    override fun start() {
        commands.forEach { it.schedule() }
        super.start()
    }

    override fun cycle() {
        telemetry.addData("busy", follower.isBusy)
    }
}