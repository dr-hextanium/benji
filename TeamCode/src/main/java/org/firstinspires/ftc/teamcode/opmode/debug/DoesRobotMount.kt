package org.firstinspires.ftc.teamcode.opmode.debug

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.opmode.BasedOpMode
import kotlin.math.sin

@TeleOp(group = "Debug")
class DoesRobotMount : BasedOpMode() {
    override fun initialize() { }

    override fun loop() {
        Robot.Motors.fl.power = sin(runtime) / 2.0

        telemetry.addData("power", Robot.Motors.fl)
    }
}