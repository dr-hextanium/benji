package org.firstinspires.ftc.teamcode.command.core

import org.firstinspires.ftc.teamcode.hardware.subsystems.Claw.Companion.CLOSED
import org.firstinspires.ftc.teamcode.hardware.subsystems.Claw.Companion.OPEN
import org.firstinspires.ftc.teamcode.hardware.subsystems.Grabber
import org.firstinspires.ftc.teamcode.utility.DelayedCommand

class OpenClaw(grabber: Grabber, delay: Long = 0) : DelayedCommand({ grabber.claw.position = OPEN }, delay)
class CloseClaw(grabber: Grabber, delay: Long = 0) : DelayedCommand({ grabber.claw.position = CLOSED }, delay)