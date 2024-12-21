package org.firstinspires.ftc.teamcode.command.core

import com.arcrobotics.ftclib.command.ConditionalCommand
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.subsystems.Grabber
import org.firstinspires.ftc.teamcode.hardware.subsystems.Lift
import org.firstinspires.ftc.teamcode.utility.DelayedCommand

class VariableTwist(position: () -> Double, grabber: Grabber, delay: Long = 0) : DelayedCommand({ grabber.twist.position = position() }, delay) {
	constructor(position: Double, grabber: Grabber, delay: Long = 0) : this({ position }, grabber, delay)
}

class NudgeTwist(deltaPosition: Double, delay: Long = 0): ConditionalCommand(
	VariableTwist({ Robot.Subsystems.front.grabber.twist.position + deltaPosition }, Robot.Subsystems.front.grabber, delay),
	VariableTwist({ Robot.Subsystems.back.grabber.twist.position + deltaPosition }, Robot.Subsystems.back.grabber, delay),
	{ Robot.Subsystems.back.extendable.position.toInt() == Lift.ZERO }
)

class ZeroTwist(grabber: Grabber, delay: Long = 0): DelayedCommand({ grabber.twist.position = 0.0 }, delay)