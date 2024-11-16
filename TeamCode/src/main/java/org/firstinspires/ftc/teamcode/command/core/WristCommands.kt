package org.firstinspires.ftc.teamcode.command.core

import com.arcrobotics.ftclib.command.InstantCommand
import org.firstinspires.ftc.teamcode.hardware.subsystems.Grabber
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist.Companion.DEFAULT
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist.Companion.DOWN
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist.Companion.TRANSFER

class WristToTransfer(grabber: Grabber) : InstantCommand({ grabber.wrist.position = TRANSFER })
class WristPointsDown(grabber: Grabber) : InstantCommand({ grabber.wrist.position = DOWN })
class WristToDefault(grabber: Grabber) : InstantCommand({ grabber.wrist.position = DEFAULT })

class VariableWrist(position: Double, grabber: Grabber) : InstantCommand({ grabber.wrist.position = position })
