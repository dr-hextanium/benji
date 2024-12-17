package org.firstinspires.ftc.teamcode.opmode.debug

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.arcrobotics.ftclib.controller.PIDController
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import kotlin.math.sign

@TeleOp
@Config
class ExtendoTuner : OpMode() {
    private val controller = PIDController(kP, kI, kD)

//    private var extendoProfile = AsymmetricMotionProfile(0.0, 1.0, Constraints(0.0, 0.0, 0.0))
//    private val timer = ElapsedTime()

    private val position: Double
        get() = asInches(extendo.currentPosition)

    private val extendo by lazy {
        hardwareMap["extendo"] as DcMotorEx
    }

    private fun asInches(ticks: Int) = ticks.toDouble() / ticksPerInch

//    private fun newProfile(targetPos: Double, constraints: Constraints) {
//        extendoProfile = AsymmetricMotionProfile(position, targetPos, constraints)
//    }

    override fun init() {
        telemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)

        controller.setTolerance(0.005)

        extendo.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        extendo.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
    }

    override fun loop() {
//        newProfile(target, Constraints(MAX_VEL, MAX_ACCEL, MAX_DECEL))
        controller.setPID(kP, kI, kD)

//        val extendoMotionState = extendoProfile.calculate(timer.time())

        val pid = controller.calculate(-position, target)

        val power = pid + f * (target - (-position)).sign

        if (!controller.atSetPoint()) {
            extendo.power = power
        } else {
            extendo.power = 0.0
        }

        extendo.power = power

        telemetry.addData("position ", -position)
        telemetry.addData("target ", target)
//        telemetry.addData("time ", timer.time())
//        telemetry.addData("current", extendo.getCurrent(CurrentUnit.AMPS))
//        telemetry.addData("profile x", extendoMotionState.x)
//        telemetry.addData("profile v", extendoMotionState.v)
//        telemetry.addData("profile a", extendoMotionState.a)
        telemetry.update()
    }

    companion object {
        // extendo: kP = 0.6, kI = 0.0, kD = 0.0, f = 0.0
        @JvmField
        var kP = 0.0
        @JvmField
        var kI = 0.0
        @JvmField
        var kD = 0.0
        @JvmField
        var f = 0.0
        @JvmField
        var target = 0.0

//        @JvmField
//        var MAX_ACCEL = 0.0
//        @JvmField
//        var MAX_VEL = 0.0
//        @JvmField
//        var MAX_DECEL = 0.0

        const val ticksPerInch = 220.0
    }
}