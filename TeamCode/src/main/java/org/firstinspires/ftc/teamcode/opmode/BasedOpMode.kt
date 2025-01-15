package org.firstinspires.ftc.teamcode.opmode

import com.arcrobotics.ftclib.command.CommandScheduler
import com.arcrobotics.ftclib.command.button.GamepadButton
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.command.core.CloseClaw
import org.firstinspires.ftc.teamcode.command.core.OpenClaw
import org.firstinspires.ftc.teamcode.command.core.ToIntake
import org.firstinspires.ftc.teamcode.hardware.Globals
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems.back
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems.front
import org.firstinspires.ftc.teamcode.opmode.CommandSequencing.Companion.SQUARE

abstract class BasedOpMode : OpMode() {
	private val gamepad by lazy { Robot.gamepad1 }

	var timer = ElapsedTime()

	abstract fun initialize()

	override fun init() {
		Globals.AUTO = false

		Robot.init(hardwareMap, telemetry, gamepad1, gamepad2)

		val gamepad = Robot.gamepad1

		GamepadButton(gamepad, GamepadKeys.Button.DPAD_UP).whenPressed(OpenClaw(back.grabber))
		GamepadButton(gamepad, GamepadKeys.Button.DPAD_DOWN).whenPressed(CloseClaw(back.grabber))

		initialize()

		timer.reset()
	}

	abstract fun cycle()

	override fun init_loop() {
		Robot.hubs.forEach { it.clearBulkCache() }

		Robot.read()
		Robot.update()

		cycle()

		Robot.scheduler.run()

		Robot.write()

		telemetry.addData("left x", gamepad.leftX)
		telemetry.addData("left y", gamepad.leftY)
		telemetry.addData("right y", gamepad.rightY)
		telemetry.addData("hz", 1000.0 / timer.milliseconds())

		timer.reset()
	}

	override fun loop() {
		Robot.hubs.forEach { it.clearBulkCache() }

		Robot.read()
		Robot.update()

		cycle()

		Robot.scheduler.run()

		if (!Globals.AUTO) {
			Subsystems.drive.driveRobotCentric(
				-Robot.gamepad1.leftX,
				-Robot.gamepad1.leftY,
				-Robot.gamepad1.rightX *
						if (front.extendable.target != 0) (7.0 / 17.0) else 1.0
			)
		}

		Robot.write()

		telemetry.addData("left x", gamepad.leftX)
		telemetry.addData("left y", gamepad.leftY)
		telemetry.addData("right y", gamepad.rightY)
		telemetry.addData("hz", 1000.0 / timer.milliseconds())

		timer.reset()
	}
}