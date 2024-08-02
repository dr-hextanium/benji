package org.firstinspires.ftc.teamcode.hardware.drive.swerve

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.normalizeRadians
import org.firstinspires.ftc.teamcode.hardware.devices.AbsoluteAnalogEncoder
import org.firstinspires.ftc.teamcode.hardware.devices.CachingDcMotor
import org.firstinspires.ftc.teamcode.hardware.drive.swerve.SwerveModule.ModuleConfig.P
import org.firstinspires.ftc.teamcode.hardware.drive.swerve.SwerveModule.ModuleConfig.I
import org.firstinspires.ftc.teamcode.hardware.drive.swerve.SwerveModule.ModuleConfig.D
import org.firstinspires.ftc.teamcode.hardware.drive.swerve.SwerveModule.ModuleConfig.F
import org.firstinspires.ftc.teamcode.hardware.drive.swerve.SwerveModule.ModuleConfig.MOTOR_FLIPPING
import org.firstinspires.ftc.teamcode.hardware.subsystems.ISubsystem
import org.firstinspires.ftc.teamcode.utility.PIDFController
import org.firstinspires.ftc.teamcode.utility.geometry.Angle.deg
import kotlin.math.PI
import kotlin.math.abs

class SwerveModule(
    val motor: CachingDcMotor,
    val servo: CRServo,
    val encoder: AbsoluteAnalogEncoder
) : ISubsystem {
    val controller = PIDFController(P, I, D, F)

    var position = 0.0
        get() = normalizeRadians(field - PI)
        set(value) { field = normalizeRadians(value) }

    var target = 0.0
        get() = normalizeRadians(field - PI)
        set(value) { field = normalizeRadians(value) }

    val error
        get() = normalizeRadians(target - position)

    var flipped = false

    var drivePower = 0.0

    init {
        motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        reset()
    }

    override fun reset() {
        motor.power = 0.0
        servo.power = 0.0
        encoder.zero(encoder.position)
    }

    override fun read() { position = encoder.position }

    override fun update() {
        flipped = MOTOR_FLIPPING && abs(error) > 90.deg

        if (flipped) target = normalizeRadians(target - PI)
    }

    override fun write() {
        motor.power = drivePower

        servo.power = controller.coeffs(P, I, D, F)
            .target(error)
            .calculate(0.0)
    }

    fun setDrivePower(power: Double) { drivePower = power }

    @Config
    object ModuleConfig {
        @JvmField
        val P = 0.0
        @JvmField
        val I = 0.0
        @JvmField
        val D = 0.0
        @JvmField
        val F = 0.0

        @JvmField
        val MOTOR_FLIPPING = true
    }
}