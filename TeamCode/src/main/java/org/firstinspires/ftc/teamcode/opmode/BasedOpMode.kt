package org.firstinspires.ftc.teamcode.opmode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.hardware.Robot

abstract class BasedOpMode : OpMode() {
    override fun init() {
        Robot.init(hardwareMap, telemetry)
    }
}