package org.firstinspires.ftc.teamcode.opmode.auto

import org.firstinspires.ftc.teamcode.hardware.Globals
import org.firstinspires.ftc.teamcode.opmode.BasedOpMode


abstract class AutoOpMode : BasedOpMode() {
    override fun init() {
        super.init()
        Globals.AUTO = true
    }
}