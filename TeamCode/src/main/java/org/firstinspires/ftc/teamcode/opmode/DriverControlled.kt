package org.firstinspires.ftc.teamcode.opmode

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.command.core.CloseClaw
import org.firstinspires.ftc.teamcode.command.core.OpenClaw
import org.firstinspires.ftc.teamcode.hardware.Robot

@TeleOp
class DriverControlled : BasedOpMode() {
	override fun initialize() {
	}

	override fun cycle() {
		if (gamepad1.cross) {
			Robot.scheduler.schedule(CloseClaw(Robot.Subsystems.front.grabber))
		}

		if (gamepad1.triangle) {
			Robot.scheduler.schedule(OpenClaw(Robot.Subsystems.front.grabber))
		}
	}
}