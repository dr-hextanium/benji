package org.firstinspires.ftc.teamcode.opmode

import com.arcrobotics.ftclib.command.button.GamepadButton
import com.arcrobotics.ftclib.gamepad.GamepadEx
import com.arcrobotics.ftclib.gamepad.GamepadKeys.*
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.command.core.CloseClaw
import org.firstinspires.ftc.teamcode.command.core.ElbowPointsDown
import org.firstinspires.ftc.teamcode.command.core.ElbowToTransfer
import org.firstinspires.ftc.teamcode.command.core.WristPointsDown
import org.firstinspires.ftc.teamcode.command.core.WristToTransfer
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems.front

abstract class BasedOpMode : OpMode() {
	abstract fun initialize()

	override fun init() {
		Robot.init(hardwareMap, telemetry, gamepad1, gamepad2)
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