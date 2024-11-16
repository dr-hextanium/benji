package org.firstinspires.ftc.teamcode.hardware.devices

import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.hardware.Globals
import kotlin.math.abs

class CachingDcMotor(val motor: DcMotor, private val threshold: Double = Globals.DRIVE_MOTOR_THRESHOLD) : DcMotor by motor {
    var previous = 0.0

    override fun setPower(power: Double) {
        if (abs(previous - power) >= threshold) {
            previous = power
            motor.power = power
        }
    }
}