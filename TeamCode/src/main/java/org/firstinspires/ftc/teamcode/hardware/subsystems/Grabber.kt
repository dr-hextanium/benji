package org.firstinspires.ftc.teamcode.hardware.subsystems

class Grabber(
	val claw: Claw,
	val twist: Twist,
	val wrist: Wrist,
	val elbow: Elbow
) : ISubsystem {
	private fun all() = listOf(claw, twist, wrist, elbow) as List<ISubsystem>

	override fun reset() {
		all().forEach { it.reset() }
	}

	override fun read() {
		all().forEach { it.read() }
	}

	override fun update() {
		all().forEach { it.update() }
	}

	override fun write() {
		all().forEach { it.write() }
	}
}