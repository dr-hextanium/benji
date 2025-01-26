package org.firstinspires.ftc.teamcode.opmode

import com.arcrobotics.ftclib.command.CommandScheduler
import com.arcrobotics.ftclib.command.InstantCommand
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
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems.drive
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems.front
import org.firstinspires.ftc.teamcode.hardware.subsystems.Extendo
import org.firstinspires.ftc.teamcode.hardware.subsystems.Lift
import org.firstinspires.ftc.teamcode.opmode.CommandSequencing.Companion.SQUARE
import kotlin.math.pow

abstract class BasedOpMode : OpMode() {
	private val gamepad by lazy { Robot.gamepad1 }

	var timer = ElapsedTime()

	private var driveSlow = false
	private var count = 0

	abstract fun initialize()

	override fun init() {
		Globals.AUTO = false

		Robot.init(hardwareMap, telemetry, gamepad1, gamepad2)

		val gamepad = Robot.gamepad1

		GamepadButton(gamepad, GamepadKeys.Button.DPAD_UP).whenPressed(OpenClaw(back.grabber))
		GamepadButton(gamepad, GamepadKeys.Button.DPAD_DOWN).whenPressed(CloseClaw(back.grabber))

		GamepadButton(gamepad, GamepadKeys.Button.DPAD_RIGHT).whenPressed(InstantCommand({ driveSlow = !driveSlow; }))
		GamepadButton(gamepad, GamepadKeys.Button.B).whenPressed(InstantCommand({ driveSlow = false; }))

		GamepadButton(gamepad, GamepadKeys.Button.LEFT_STICK_BUTTON).whenPressed(InstantCommand({ Subsystems.otos.resetPose() }))
		GamepadButton(gamepad, GamepadKeys.Button.RIGHT_STICK_BUTTON).whenPressed(InstantCommand({ (back.extendable as Lift).reset() }))

		GamepadButton(Robot.gamepad2, GamepadKeys.Button.DPAD_RIGHT).whenPressed(InstantCommand({ driveSlow = !driveSlow; }))
		GamepadButton(Robot.gamepad2, GamepadKeys.Button.B).whenPressed(InstantCommand({ driveSlow = false; }))

		GamepadButton(Robot.gamepad2, GamepadKeys.Button.LEFT_STICK_BUTTON).whenPressed(InstantCommand({ Subsystems.otos.resetPose() }))
		GamepadButton(Robot.gamepad2, GamepadKeys.Button.RIGHT_STICK_BUTTON).whenPressed(InstantCommand({ (back.extendable as Lift).reset() }))

		initialize()

		timer.reset()
	}

	abstract fun cycle()

	override fun init_loop() {
		Robot.hubs.forEach { it.clearBulkCache() }

		Robot.read()
//		Robot.update()

		cycle()

		Robot.scheduler.run()

//		Robot.write()

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

//		if(gamepad.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)) {
//			telemetry.addLine("right pressed")
//			driveSlow = true
//		} else if(gamepad.wasJustPressed((GamepadKeys.Button.B))) {
//			telemetry.addLine("transfer pressed")
//			driveSlow = false
//		}

		cycle()

		Robot.scheduler.run()


		if (!Globals.AUTO) {
//			drive.driveRobotCentric(
//				-Robot.gamepad1.leftX.pow(3)
//					* if(driveSlow) 0.25 else 1.0,
//				-Robot.gamepad1.leftY.pow(3)
//					* if(driveSlow) 0.25 else 1.0,
//				-Robot.gamepad1.rightX
//						* if (driveSlow) 0.25 else 1.0,
//			)

			val heading = Subsystems.otos.getDegrees()

			drive.driveFieldCentric(
				-Robot.gamepad1.leftX
						* if(driveSlow) 0.25 else 1.0,
				-Robot.gamepad1.leftY
						* if(driveSlow) 0.25 else 1.0,
				-Robot.gamepad1.rightX
						* if (driveSlow) 0.25 else 1.0,
				heading
			)

			telemetry.addData("heading", heading)
		}

		Robot.write()

		telemetry.addData("left x", gamepad.leftX)
		telemetry.addData("left y", gamepad.leftY)
		telemetry.addData("right y", gamepad.rightY)
		telemetry.addData("hz", 1000.0 / timer.milliseconds())
		telemetry.addData("driveSlow", driveSlow)

		telemetry.addData("extendo pos", front.extendable.position)
		telemetry.addData("extendo target", front.extendable.target)
		telemetry.addData("extendo power", (front.extendable as Extendo).power)

		telemetry.addData("color", Subsystems.camera.color.name)
		telemetry.addData("angle", Subsystems.camera.pixelPos)

		timer.reset()
	}
}