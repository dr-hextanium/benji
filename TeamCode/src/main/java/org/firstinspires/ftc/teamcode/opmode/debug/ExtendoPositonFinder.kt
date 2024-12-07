package org.firstinspires.ftc.teamcode.opmode.debug

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple

class ExtendoPositonFinder : OpMode() {
    private val extendo by lazy {
        hardwareMap["extendo"] as DcMotorEx
    }

    private val ticksPerInch = 220;

    override fun init() {
        extendo.mode = DcMotor.RunMode.RUN_USING_ENCODER
        extendo.mode = DcMotor.RunMode.RUN_TO_POSITION
        extendo.direction = DcMotorSimple.Direction.REVERSE
    }

    override fun loop() {
        extendo.targetPosition = 10 * ticksPerInch
        extendo.mode = DcMotor.RunMode.RUN_TO_POSITION
        extendo.power = 1.0
    }
}