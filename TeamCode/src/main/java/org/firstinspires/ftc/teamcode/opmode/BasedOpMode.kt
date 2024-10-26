package org.firstinspires.ftc.teamcode.opmode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.hardware.Robot

abstract class BasedOpMode : OpMode() {
    abstract fun initialize()

    override fun init() {
        Robot.init(hardwareMap, telemetry)
        initialize()
    }

    abstract fun cycle()

    override fun loop() {
        Robot.read()
        Robot.update()
        Robot.write()

        cycle()
    }
}