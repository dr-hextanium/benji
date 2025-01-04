package org.firstinspires.ftc.teamcode.opmode

import com.arcrobotics.ftclib.command.button.GamepadButton
import com.arcrobotics.ftclib.gamepad.GamepadEx
import com.arcrobotics.ftclib.gamepad.GamepadKeys.*
import com.arcrobotics.ftclib.hardware.motors.Motor
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.command.core.CloseClaw
import org.firstinspires.ftc.teamcode.command.core.ElbowPointsDown
import org.firstinspires.ftc.teamcode.command.core.ElbowToTransfer
import org.firstinspires.ftc.teamcode.command.core.WristPointsDown
import org.firstinspires.ftc.teamcode.command.core.WristToTransfer
import org.firstinspires.ftc.teamcode.hardware.Globals
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems.front
import org.firstinspires.ftc.teamcode.hardware.drive.mecanum.MecanumDrive
import org.firstinspires.ftc.teamcode.utility.geometry.Pose2D

abstract class BasedOpMode : OpMode() {
	private val gamepad by lazy { Robot.gamepad1 }

	var timer = ElapsedTime()

	abstract fun initialize()

	override fun init() {
		Robot.init(hardwareMap, telemetry, gamepad1, gamepad2)
		initialize()
		timer.reset()

	}

	abstract fun cycle()

	override fun loop() {
		Robot.read()
		Robot.update()
		Robot.scheduler.run()
		cycle()
		Robot.write()

		Subsystems.drive.driveRobotCentric(
			-Robot.gamepad1.leftX,
			-Robot.gamepad1.leftY,
			-Robot.gamepad1.rightX *
					if (front.extendable.target != 0) (7.0 / 17.0) else 1.0
		)

		telemetry.addData("left x", gamepad.leftX)
		telemetry.addData("left y", gamepad.leftY)
		telemetry.addData("right y", gamepad.rightY)
		telemetry.addData("hz", 1000.0 / timer.milliseconds())
		timer.reset()
	}
}