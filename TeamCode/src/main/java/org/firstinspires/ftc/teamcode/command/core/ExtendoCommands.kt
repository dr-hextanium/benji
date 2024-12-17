package org.firstinspires.ftc.teamcode.command.core

import org.firstinspires.ftc.teamcode.hardware.subsystems.Extendo
import org.firstinspires.ftc.teamcode.utility.DelayedCommand

class ExtendoTo(position: Int, extendo: Extendo, delay: Long = 0) : DelayedCommand({ extendo.target = position }, delay)