package org.firstinspires.ftc.teamcode.utility

import com.arcrobotics.ftclib.command.InstantCommand
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.arcrobotics.ftclib.command.WaitCommand

abstract class DelayedCommand(toRun: Runnable, delay: Long) : SequentialCommandGroup(InstantCommand(toRun), WaitCommand(delay))