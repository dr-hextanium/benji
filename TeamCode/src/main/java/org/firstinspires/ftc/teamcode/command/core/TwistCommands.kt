package org.firstinspires.ftc.teamcode.command.core

import com.arcrobotics.ftclib.command.InstantCommand
import org.firstinspires.ftc.teamcode.hardware.subsystems.Grabber

class VariableTwist(position: Double, grabber: Grabber) : InstantCommand({ grabber.twist.position = position })
