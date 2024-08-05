package org.firstinspires.ftc.teamcode.hardware

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.robotcore.hardware.AnalogInput
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
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
        AnalogEncoders.fl = AbsoluteAnalogEncoder(hw["front left encoder"] as AnalogInput)
        AnalogEncoders.br = AbsoluteAnalogEncoder(hw["back right encoder"] as AnalogInput)
        AnalogEncoders.bl = AbsoluteAnalogEncoder(hw["back left encoder"] as AnalogInput)
    }

    override fun reset() {
        TODO("Not yet implemented")
    }

    override fun read() {
        TODO("Not yet implemented")
    }

    override fun update() {
        TODO("Not yet implemented")
    }

    override fun write() {
        TODO("Not yet implemented")
    }
}