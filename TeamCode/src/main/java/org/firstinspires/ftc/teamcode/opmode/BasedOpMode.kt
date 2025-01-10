package org.firstinspires.ftc.teamcode.opmode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.hardware.Globals
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems
import org.firstinspires.ftc.teamcode.hardware.Robot.Subsystems.front

abstract class BasedOpMode : OpMode() {
	private val gamepad by lazy { Robot.gamepad1 }

	var timer = ElapsedTime()

	abstract fun initialize()

	override fun init() {
		Globals.AUTO = false

		Robot.init(hardwareMap, telemetry, gamepad1, gamepad2)
		initialize()
		timer.reset()
	}

	abstract fun cycle()

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