package org.firstinspires.ftc.teamcode.command.auto

import com.arcrobotics.ftclib.command.ParallelCommandGroup
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import org.firstinspires.ftc.teamcode.command.core.LiftTo
import org.firstinspires.ftc.teamcode.command.core.VariableElbow
import org.firstinspires.ftc.teamcode.command.core.VariableTwist
import org.firstinspires.ftc.teamcode.command.core.VariableWrist
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow
import org.firstinspires.ftc.teamcode.hardware.subsystems.Lift
import org.firstinspires.ftc.teamcode.hardware.subsystems.Twist
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist

class DepositToGround : SequentialCommandGroup(
    LiftTo(Lift.State.ZERO, Robot.Subsystems.back.extendable as Lift, 100),
    ParallelCommandGroup(
        VariableWrist(Wrist.BACK_TO_TRANSFER, Robot.Subsystems.back.wrist),
        VariableElbow(Elbow.BACK_TO_TRANSFER, Robot.Subsystems.back.elbow),
        VariableTwist(Twist.BACK_TRANSFER, Robot.Subsystems.back.grabber)
    )
)