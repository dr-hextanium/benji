package org.firstinspires.ftc.teamcode.command.core

import com.arcrobotics.ftclib.command.InstantCommand
import org.firstinspires.ftc.teamcode.hardware.subsystems.Claw.Companion.CLOSED
import org.firstinspires.ftc.teamcode.hardware.subsystems.Claw.Companion.OPEN
import org.firstinspires.ftc.teamcode.hardware.subsystems.Grabber

class OpenClaw(grabber: Grabber) : InstantCommand({ grabber.claw.position = OPEN })
class CloseClaw(grabber: Grabber) : InstantCommand({ grabber.claw.position = CLOSED })
