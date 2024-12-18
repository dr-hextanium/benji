package org.firstinspires.ftc.teamcode.opmode

import com.arcrobotics.ftclib.command.button.GamepadButton
import com.arcrobotics.ftclib.gamepad.GamepadEx
import com.arcrobotics.ftclib.gamepad.GamepadKeys.*
import com.arcrobotics.ftclib.hardware.motors.Motor
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.teamcode.command.core.CloseClaw
import org.firstinspires.ftc.teamcode.command.core.ElbowPointsDown
import org.firstinspires.ftc.teamcode.command.core.ElbowToTransfer
import org.firstinspires.ftc.teamcode.command.core.WristPointsDown
import org.firstinspires.ftc.teamcode.command.core.WristToTransfer
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems.front
import org.firstinspires.ftc.teamcode.hardware.drive.mecanum.MecanumDrive

abstract class BasedOpMode : OpMode() {
	private val drive by lazy { com.arcrobotics.ftclib.drivebase.MecanumDrive(Motor(hardwareMap, "frontLeft"), Motor(hardwareMap, "frontRight"), Motor(hardwareMap, "backLeft"), Motor(hardwareMap, "backRight")) }

	private val gamepad by lazy { Robot.gamepad1 }

	abstract fun initialize()

	override fun init() {
		Robot.init(hardwareMap, telemetry, gamepad1, gamepad2)
		initialize()
	}

	abstract fun cycle()

	override fun loop() {
		Robot.read()
		Robot.update()
		Robot.scheduler.run()
		cycle()
		Robot.write()

		drive.driveRobotCentric(
			gamepad.leftX,
			gamepad.leftY,
			gamepad.rightY
		)
	}
}