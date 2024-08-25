package org.firstinspires.ftc.teamcode.hardware

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.hardware.AnalogInput
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.hardware.Robot.Servos.Offsets.bl
import org.firstinspires.ftc.teamcode.hardware.Robot.Servos.Offsets.br
import org.firstinspires.ftc.teamcode.hardware.Robot.Servos.Offsets.fl
import org.firstinspires.ftc.teamcode.hardware.Robot.Servos.Offsets.fr
import org.firstinspires.ftc.teamcode.hardware.devices.AbsoluteAnalogEncoder
import org.firstinspires.ftc.teamcode.hardware.devices.CachingDcMotor
import org.firstinspires.ftc.teamcode.hardware.subsystems.ISubsystem

/**
 * Not proud of this, but it does honestly make things easier in the long run.
 *
 * The idea is you have one source to get all of your hardware from, which
 * translates to having less dependency hell with passing down a `HardwareMap`
 * to all receivers.
 */
object Robot : ISubsystem {
    lateinit var telemetry: MultipleTelemetry
    lateinit var hw: HardwareMap

    object Motors {
        lateinit var fr: CachingDcMotor
        lateinit var fl: CachingDcMotor
        lateinit var br: CachingDcMotor
        lateinit var bl: CachingDcMotor

        fun all() = listOf(fr, fl, br, bl)
    }

    object Servos {
        lateinit var fr: CRServo
        lateinit var fl: CRServo
        lateinit var br: CRServo
        lateinit var bl: CRServo

        @Config
        object Offsets {
            @JvmField
            var fr = 0.0
            @JvmField
            var fl = 0.0
            @JvmField
            var br = 0.0
            @JvmField
            var bl = 0.0
        }

        fun all() = listOf(fr, fl, br, bl)
    }

    object AnalogEncoders {
        lateinit var fr: AbsoluteAnalogEncoder
        lateinit var fl: AbsoluteAnalogEncoder
        lateinit var br: AbsoluteAnalogEncoder
        lateinit var bl: AbsoluteAnalogEncoder

        fun all() = listOf(fr, fl, br, bl)
    }

    fun init(hw: HardwareMap, telemetry: Telemetry) {
        this.telemetry = MultipleTelemetry(FtcDashboard.getInstance().telemetry, telemetry)
        this.hw = hw

        Motors.fr = CachingDcMotor(hw["front right drive"] as DcMotor, Globals.DRIVE_MOTOR_THRESHOLD)
        Motors.fl = CachingDcMotor(hw["front left drive"] as DcMotor, Globals.DRIVE_MOTOR_THRESHOLD)
        Motors.br = CachingDcMotor(hw["back right drive"] as DcMotor, Globals.DRIVE_MOTOR_THRESHOLD)
        Motors.bl = CachingDcMotor(hw["back left drive"] as DcMotor, Globals.DRIVE_MOTOR_THRESHOLD)

        Servos.fr = hw["front right steer"] as CRServo
        Servos.fl = hw["front left steer"] as CRServo
        Servos.br = hw["back right steer"] as CRServo
        Servos.bl = hw["back left steer"] as CRServo

        AnalogEncoders.fr = AbsoluteAnalogEncoder(hw["front right encoder"] as AnalogInput)
            .zero(fr)
        AnalogEncoders.fl = AbsoluteAnalogEncoder(hw["front left encoder"] as AnalogInput)
            .zero(fl)
        AnalogEncoders.br = AbsoluteAnalogEncoder(hw["back right encoder"] as AnalogInput)
            .zero(br)
        AnalogEncoders.bl = AbsoluteAnalogEncoder(hw["back left encoder"] as AnalogInput)
            .zero(bl)
    }

    override fun reset() {
        TODO("Not yet implemented")
    }

    override fun read() {
        TODO("Not yet implemented")
    }

    override fun update() {
        AnalogEncoders.fr.zero(fr)
        AnalogEncoders.fl.zero(fl)
        AnalogEncoders.br.zero(br)
        AnalogEncoders.bl.zero(bl)
    }

    override fun write() {
        TODO("Not yet implemented")
    }
}