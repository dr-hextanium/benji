package org.firstinspires.ftc.teamcode.opmode.debug

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor

@TeleOp
class ExtendoGetsPower: OpMode() {
    private val extendo by lazy { hardwareMap["extendo"] as DcMotor }

    override fun init() {
        extendo.power = 0.0
    }

    override fun loop() {
        extendo.power = if(gamepad1.square) 0.5 else 0.0
    }
}