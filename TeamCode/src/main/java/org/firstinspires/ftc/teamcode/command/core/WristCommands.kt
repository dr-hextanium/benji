package org.firstinspires.ftc.teamcode.command.core

import com.arcrobotics.ftclib.command.CommandBase
import com.arcrobotics.ftclib.command.InstantCommand
import org.firstinspires.ftc.teamcode.hardware.subsystems.Grabber
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist.Companion.FRONT_DEFAULT
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist.Companion.FRONT_DOWN
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist.Companion.FRONT_TO_TRANSFER
import org.firstinspires.ftc.teamcode.utility.DelayedCommand

class WristToTransfer(grabber: Grabber, delay: Long = 0) : DelayedCommand({ grabber.wrist.position = FRONT_TO_TRANSFER }, delay)
class WristPointsDown(grabber: Grabber, delay: Long = 0) : DelayedCommand({ grabber.wrist.position = FRONT_DOWN }, delay)
class WristToDefault(grabber: Grabber, delay: Long = 0) : DelayedCommand({ grabber.wrist.position = FRONT_DEFAULT }, delay)

class VariableWrist(position: Double, wrist: Wrist, delay: Long = 0) : DelayedCommand({ wrist.position = position }, delay)
