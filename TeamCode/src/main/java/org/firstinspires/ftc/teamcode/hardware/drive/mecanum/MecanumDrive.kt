package org.firstinspires.ftc.teamcode.hardware.drive.mecanum

import org.firstinspires.ftc.teamcode.hardware.Robot.Motors
import org.firstinspires.ftc.teamcode.hardware.drive.IDrive
import org.firstinspires.ftc.teamcode.hardware.subsystems.ISubsystem
import org.firstinspires.ftc.teamcode.utility.geometry.Pose2D
import kotlin.math.PI
import kotlin.math.sin

class MecanumDrive : IDrive, ISubsystem {
	var motorsToPowers = mapOf(
		Motors.fr to 0.0,
		Motors.fl to 0.0,
		Motors.br to 0.0,
		Motors.bl to 0.0,
	)

	override fun reset() {
		Motors.all().forEach { it.power = 0.0 }
	}

	override fun read() {}

	fun move(pose: Pose2D, theta: Double) = move(pose.rotate(theta))

	override fun move(pose: Pose2D) {
		val heading = pose.position.theta
		val magnitude = pose.position.magnitude

		val red = sin(heading - PI / 4) * magnitude
		val blue = sin(heading + PI / 4) * magnitude

		motorsToPowers = mapOf(
			Motors.fr to red,
			Motors.bl to red,
			Motors.fl to blue,
			Motors.br to blue,
		)
			.let {
				(it.values.max())
					.let {
						max -> it.mapValues { (_, value) -> value / max }
					}
			}
	}

	override fun update() {

	}

	override fun write() {
		motorsToPowers.forEach { (motor, power) -> motor.power = power }
	}
}