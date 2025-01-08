package org.firstinspires.ftc.teamcode.opmode.auto

import org.firstinspires.ftc.teamcode.opmode.BasedOpMode
import org.firstinspires.ftc.teamcode.utility.SquIDController


abstract class AutoOpMode : BasedOpMode() {
    val controller = SquIDController()

    abstract fun buildPaths()

    override fun init() {
        buildPaths()
    }

    override fun loop() {
        super.loop()

//        telemetry.addData("x", follower.pose.x)
//        telemetry.addData("y", follower.pose.y)
//        telemetry.addData("heading", follower.pose.heading)
    }
}