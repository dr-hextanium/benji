package org.firstinspires.ftc.teamcode.opmode.debug

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx

@TeleOp
class EncoderPositionFinder : OpMode() {
    private val extendo by lazy { hardwareMap["extendo"] as DcMotorEx }

    override fun init() {
        extendo.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
    }

    override fun loop() {
        telemetry.addData("position", extendo.currentPosition)
    }
}