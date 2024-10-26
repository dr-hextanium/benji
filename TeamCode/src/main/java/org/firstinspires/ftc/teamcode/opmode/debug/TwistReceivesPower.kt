package org.firstinspires.ftc.teamcode.opmode.debug

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.opmode.BasedOpMode
import kotlin.math.sin

@TeleOp(group = "Debug")
class TwistReceivesPower : BasedOpMode() {
	override fun initialize() { }

	override fun cycle() {
		val position = sin(runtime) / 2.0 + 0.5

		 Robot.Subsystems.front.twist.position = position

		telemetry.addData("position", position)
	}
}