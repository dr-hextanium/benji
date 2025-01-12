package org.firstinspires.ftc.teamcode.opmode.auto

import com.pedropathing.follower.Follower
import com.pedropathing.localization.Pose
import com.pedropathing.util.Constants
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.hardware.Globals
import org.firstinspires.ftc.teamcode.opmode.BasedOpMode
import org.firstinspires.ftc.teamcode.pedro.constants.FConstants
import org.firstinspires.ftc.teamcode.pedro.constants.LConstants

abstract class AutoOpMode(val start: Pose) : BasedOpMode() {
    lateinit var follower: Follower
    lateinit var runtime: ElapsedTime

    override fun init() {
        super.init()
        Globals.AUTO = true

        Constants.setConstants(FConstants::class.java, LConstants::class.java)

        runtime = ElapsedTime()

        follower = Follower(hardwareMap)
        follower.setStartingPose(start)

        paths()
    }

    override fun start() { runtime.reset() }

    abstract fun paths()

    override fun loop() {
        cycle()

        follower.update()

        super.loop()

        telemetry.addData("x", follower.pose.x)
        telemetry.addData("y", follower.pose.y)
        telemetry.addData("heading", follower.pose.heading)
    }
}