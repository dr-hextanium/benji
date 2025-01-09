package org.firstinspires.ftc.teamcode.command.auto

import com.acmerobotics.dashboard.config.Config
import com.arcrobotics.ftclib.command.Command
import com.arcrobotics.ftclib.command.CommandBase
import com.arcrobotics.ftclib.geometry.Pose2d
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.command.auto.GoToPointCommand.GoToConfig.X_PID
import org.firstinspires.ftc.teamcode.command.auto.GoToPointCommand.GoToConfig.Y_PID
import org.firstinspires.ftc.teamcode.command.auto.GoToPointCommand.GoToConfig.H_PID
import org.firstinspires.ftc.teamcode.command.auto.GoToPointCommand.GoToConfig.MAX_HEADING
import org.firstinspires.ftc.teamcode.command.auto.GoToPointCommand.GoToConfig.MAX_HEADING_ERROR
import org.firstinspires.ftc.teamcode.command.auto.GoToPointCommand.GoToConfig.MAX_POWER
import org.firstinspires.ftc.teamcode.command.auto.GoToPointCommand.GoToConfig.MAX_LINEAR_ERROR
import org.firstinspires.ftc.teamcode.command.auto.GoToPointCommand.GoToConfig.RUNTIME
import org.firstinspires.ftc.teamcode.hardware.drive.Localizer
import org.firstinspires.ftc.teamcode.hardware.drive.mecanum.CachingMecanumDrive
import org.firstinspires.ftc.teamcode.utility.SquIDController
import java.util.concurrent.TimeUnit
import kotlin.math.PI
import kotlin.math.abs

class GoToPointCommand(
    val target: Pose2d,
    val drive: CachingMecanumDrive,
    val localizer: Localizer
) : CommandBase(), Command {
    val xController = SquIDController(X_PID)
    val yController = SquIDController(Y_PID)
    val hController = SquIDController(H_PID)

    val timer = ElapsedTime()

    override fun initialize() {
        timer.reset()
    }

    override fun execute() {
        val pose = localizer.getPose()

        val headingError = (target.heading - pose.heading).let {
            when {
                it > PI -> it - 2.0 * PI
                it < -PI -> it + 2.0 * PI
                else -> it
            }
        }

        val x = xController.calculate(pose.x, target.x).coerceIn(-MAX_POWER, MAX_POWER)
        val y = yController.calculate(pose.y, target.y).coerceIn(-MAX_POWER, MAX_POWER)
        val h = hController.calculate(pose.heading, pose.heading + headingError).coerceIn(-MAX_HEADING, MAX_HEADING)

        println("PID X: $x, Y: $y, H: $h")

        drive.driveFieldCentric(-x, -y, -h, pose.heading)
    }

    override fun isFinished(): Boolean {
        val pose = localizer.getPose()

        val dh = target.heading - pose.heading
        val dx = target.x - pose.x
        val dy = target.y - pose.y

        val atX = abs(dx) < MAX_LINEAR_ERROR
        val atY = abs(dy) < MAX_LINEAR_ERROR
        val atH = abs(dh) < MAX_HEADING_ERROR

        val overtime = timer.time(TimeUnit.SECONDS) > RUNTIME

        return (overtime) || (atX && atY && atH)
    }

    @Config
    object GoToConfig {
        @JvmField
        var MAX_LINEAR_ERROR = 1.0 // inches

        @JvmField
        var MAX_HEADING_ERROR = Math.toRadians(1.0) // radians

        @JvmField
        var MAX_POWER = 0.6

        @JvmField
        var MAX_HEADING = 0.6

        @JvmField
        var RUNTIME = 3.0 // seconds

        @JvmField
        var X_PID = doubleArrayOf(0.0, 0.0, 0.0, 0.0)

        @JvmField
        var Y_PID = doubleArrayOf(0.0, 0.0, 0.0, 0.0)

        @JvmField
        var H_PID = doubleArrayOf(0.0, 0.0, 0.0, 0.0)
    }
}