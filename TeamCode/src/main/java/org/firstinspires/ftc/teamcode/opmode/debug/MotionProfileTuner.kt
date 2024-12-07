package org.firstinspires.ftc.teamcode.opmode.debug

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.arcrobotics.ftclib.controller.PIDController
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.opmode.debug.ExtendoTuner.Companion.target
import org.firstinspires.ftc.teamcode.utility.motion.profile.AsymmetricMotionProfile
import org.firstinspires.ftc.teamcode.utility.motion.profile.Constraints
import kotlin.math.sign

@TeleOp
@Config
class MotionProfileTuner : OpMode() {
    private val controller = PIDController(0.07, 0.0, 0.0)
    private var extendoProfile = AsymmetricMotionProfile(0.0, 1.0, Constraints(0.0, 0.0, 0.0))

    private val timer = ElapsedTime()

    private val ticksPerInch = 220

    var time = 0.0

    private val extendo by lazy {
        hardwareMap["extendo"] as DcMotorEx
    }

    private fun asInches(ticks: Int) = ticks / ticksPerInch

    private fun newProfile(targetPos: Double, constraints: Constraints) {
        constraints.convert(ticksPerInch.toDouble())
        extendoProfile = AsymmetricMotionProfile(extendo.currentPosition.toDouble(), targetPos, constraints)

    }

    override fun init() {
        telemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)

        extendo.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        extendo.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
    }

    override fun loop() {
        newProfile(target, Constraints(MAX_VEL, MAX_ACCEL, MAX_DECEL))

        val extendoPos = asInches(-extendo.currentPosition)

        extendoProfile.calculate(timer.time())
        if (extendoPos != extendoProfile.finalPosition.toInt()) {

            timer.reset()
        }

        val pid = controller.calculate(extendoPos.toDouble(), target)

        val power = pid + kS * pid.sign

        extendo.power = power

        telemetry.addData("position ", extendoPos)
        telemetry.addData("target ", target)
        telemetry.update()
    }

    companion object {
        @JvmField
        var MAX_ACCEL = 0.0
        @JvmField
        var MAX_VEL = 0.0
        @JvmField
        var MAX_DECEL = 0.0
        @JvmField
        var kS = 0.1
    }
}