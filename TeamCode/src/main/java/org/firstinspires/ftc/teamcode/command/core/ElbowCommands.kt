package org.firstinspires.ftc.teamcode.command.core

import com.arcrobotics.ftclib.command.CommandBase
import com.arcrobotics.ftclib.command.InstantCommand
import com.arcrobotics.ftclib.command.WaitCommand
import org.firstinspires.ftc.teamcode.hardware.subsystems.Claw
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow.Companion.FRONT_DEFAULT
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow.Companion.FRONT_DOWN
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow.Companion.FRONT_TO_TRANSFER
import org.firstinspires.ftc.teamcode.hardware.subsystems.Grabber
import org.firstinspires.ftc.teamcode.utility.DelayedCommand

class ElbowToTransfer(grabber: Grabber, delay: Long = 0) : DelayedCommand({ grabber.elbow.position = FRONT_TO_TRANSFER }, delay)
class ElbowPointsDown(grabber: Grabber, delay: Long = 0) : DelayedCommand({ grabber.elbow.position = FRONT_DOWN }, delay)
class ElbowToDefault(grabber: Grabber, delay: Long = 0) : DelayedCommand({ grabber.elbow.position = FRONT_DEFAULT }, delay)

class VariableElbow(position: Double, elbow: Elbow, delay: Long = 0) : DelayedCommand({ elbow.position = position }, delay)