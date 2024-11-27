package org.firstinspires.ftc.teamcode.command.core

import com.arcrobotics.ftclib.command.InstantCommand
import org.firstinspires.ftc.teamcode.hardware.subsystems.Grabber
import org.firstinspires.ftc.teamcode.utility.DelayedCommand

class VariableTwist(position: () -> Double, grabber: Grabber, delay: Long = 0) : DelayedCommand({ grabber.twist.position = position() }, delay) {
	constructor(position: Double, grabber: Grabber, delay: Long) : this({ position }, grabber, delay)
}