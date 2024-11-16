package org.firstinspires.ftc.teamcode.command.core

import com.arcrobotics.ftclib.command.InstantCommand
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow.Companion.DEFAULT
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow.Companion.DOWN
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow.Companion.TRANSFER
import org.firstinspires.ftc.teamcode.hardware.subsystems.Grabber

class ElbowToTransfer(grabber: Grabber) : InstantCommand({ grabber.elbow.position = TRANSFER })
class ElbowPointsDown(grabber: Grabber) : InstantCommand({ grabber.elbow.position = DOWN })
class ElbowToDefault(grabber: Grabber) : InstantCommand({ grabber.elbow.position = DEFAULT })

class VariableElbow(position: Double, grabber: Grabber) : InstantCommand({ grabber.elbow.position = position })