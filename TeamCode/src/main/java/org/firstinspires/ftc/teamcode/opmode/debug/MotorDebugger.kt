package org.firstinspires.ftc.teamcode.opmode.debug

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.opmode.BasedOpMode

@TeleOp
class MotorDebugger : BasedOpMode() {
	override fun initialize() { }

	private fun booleanToInt(boolean: Boolean) = if (boolean) 1.0 else 0.0

	override fun cycle() {
		Robot.Motors.fl.power = booleanToInt(gamepad1.square)
		Robot.Motors.fr.power = booleanToInt(gamepad1.triangle)
		Robot.Motors.bl.power = booleanToInt(gamepad1.cross)
		Robot.Motors.br.power = booleanToInt(gamepad1.circle)
	}
}