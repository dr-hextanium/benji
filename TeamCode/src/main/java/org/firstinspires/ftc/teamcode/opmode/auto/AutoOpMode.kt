package org.firstinspires.ftc.teamcode.opmode.auto

import org.firstinspires.ftc.teamcode.auto.follower.Follower
import org.firstinspires.ftc.teamcode.auto.localization.Pose
import org.firstinspires.ftc.teamcode.auto.util.Timer
import org.firstinspires.ftc.teamcode.opmode.BasedOpMode


abstract class AutoOpMode(val start: Pose) : BasedOpMode() {
    val opmodeTimer = Timer()
    val pathTimer = Timer()

    lateinit var follower: Follower

    abstract fun buildPaths()

    override fun init() {
        opmodeTimer.resetTimer()
        pathTimer.resetTimer()

        follower = Follower(hardwareMap)
        follower.setStartingPose(start)

        buildPaths()

        super.init()
    }

    override fun loop() {
        super.loop()
        follower.update()

        telemetry.addData("x", follower.pose.x)
        telemetry.addData("y", follower.pose.y)
        telemetry.addData("heading", follower.pose.heading)
    }
}