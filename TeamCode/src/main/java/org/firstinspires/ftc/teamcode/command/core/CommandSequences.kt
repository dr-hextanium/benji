package org.firstinspires.ftc.teamcode.command.core

import com.arcrobotics.ftclib.command.ConditionalCommand
import com.arcrobotics.ftclib.command.ParallelCommandGroup
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.arcrobotics.ftclib.command.WaitCommand
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.subsystems.Claw
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow
import org.firstinspires.ftc.teamcode.hardware.subsystems.Extendo
import org.firstinspires.ftc.teamcode.hardware.subsystems.Lift
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist

class ToIntake : SequentialCommandGroup(
    ParallelCommandGroup(
        WristToDefault(Robot.Subsystems.front.grabber),
        ElbowToDefault(Robot.Subsystems.front.grabber),
        OpenClaw(Robot.Subsystems.front.grabber),
        ExtendoTo(Extendo.TO_INTAKE, Robot.Subsystems.front.extendable as Extendo)
    ),
    WaitCommand(100),
    ParallelCommandGroup(
        WristPointsDown(Robot.Subsystems.front.grabber),
        ElbowPointsDown(Robot.Subsystems.front.grabber)
    )
)

class Grab : ConditionalCommand(
    SequentialCommandGroup(
        OpenClaw(Robot.Subsystems.front.grabber),
        OpenClaw(Robot.Subsystems.back.grabber)
    ),
    SequentialCommandGroup(
        CloseClaw(Robot.Subsystems.front.grabber),
        CloseClaw(Robot.Subsystems.back.grabber)
    ),
    { Robot.Subsystems.front.grabber.claw.position == Claw.CLOSED }
)

//class GrabFront : ConditionalCommand(
//    OpenClaw(Robot.Subsystems.front.grabber),
//    CloseClaw(Robot.Subsystems.front.grabber),
//    { Robot.Subsystems.front.grabber.claw.position == Claw.CLOSED }
//)

//class GrabBack : ConditionalCommand(
//    OpenClaw(Robot.Subsystems.back.grabber),
//    CloseClaw(Robot.Subsystems.back.grabber),
//    { Robot.Subsystems.back.grabber.claw.position == Claw.CLOSED }
//)

class Transfer: SequentialCommandGroup(
    ParallelCommandGroup(
        LiftTo(Lift.State.ZERO, Robot.Subsystems.back.extendable as Lift),
        VariableWrist(Wrist.BACK_TO_TRANSFER, Robot.Subsystems.back.grabber.wrist),
        VariableElbow(Elbow.BACK_TO_TRANSFER, Robot.Subsystems.back.grabber.elbow),
        OpenClaw(Robot.Subsystems.back.grabber),
        ZeroTwist(Robot.Subsystems.front.grabber),
        ZeroTwist(Robot.Subsystems.back.grabber)
    ),
    WaitCommand(100),

    ParallelCommandGroup(
        ElbowToTransfer(Robot.Subsystems.front.grabber),
        WristToTransfer(Robot.Subsystems.front.grabber)
    ),
    WaitCommand(100),
    ExtendoTo(Extendo.TO_TRANSFER, Robot.Subsystems.front.extendable as Extendo, 100),

    WaitCommand(500),

    CloseClaw(Robot.Subsystems.back.grabber, 500),
    OpenClaw(Robot.Subsystems.front.grabber, 250),

    ParallelCommandGroup(
        ElbowToDefault(Robot.Subsystems.front.grabber),
        WristToDefault(Robot.Subsystems.front.grabber),
        ExtendoTo(0, Robot.Subsystems.front.extendable as Extendo)
    ),

    ParallelCommandGroup(
        VariableWrist(Wrist.BACK_DEFAULT, Robot.Subsystems.back.grabber.wrist),
        VariableElbow(Elbow.BACK_TO_DEPOSIT, Robot.Subsystems.back.grabber.elbow)
    )
)

class DepositBasket(lift: Lift): ConditionalCommand(
    SequentialCommandGroup(
        LiftTo(Lift.State.HIGH_BASKET, lift, 100),
        VariableWrist(Wrist.BACK_TO_DEPOSIT, Robot.Subsystems.back.wrist)
    ),
    SequentialCommandGroup(
        LiftTo(Lift.State.ZERO, lift),
        VariableWrist(Wrist.BACK_TO_TRANSFER, Robot.Subsystems.back.grabber.wrist),
        VariableElbow(Elbow.BACK_TO_TRANSFER, Robot.Subsystems.back.grabber.elbow),
        OpenClaw(Robot.Subsystems.back.grabber)
    ),
    { Robot.Subsystems.back.extendable.position == Lift.ZERO }
)

class DepositBar(lift: Lift): ConditionalCommand(
    SequentialCommandGroup(
        LiftTo(Lift.State.HIGH_BAR, lift, 100),
        VariableWrist(Wrist.BACK_TO_DEPOSIT, Robot.Subsystems.back.wrist)
    ),
    SequentialCommandGroup(
        LiftTo(Lift.State.ZERO, lift),
        VariableWrist(Wrist.BACK_TO_TRANSFER, Robot.Subsystems.back.grabber.wrist),
        VariableElbow(Elbow.BACK_TO_TRANSFER, Robot.Subsystems.back.grabber.elbow),
        OpenClaw(Robot.Subsystems.back.grabber)
    ),
    { Robot.Subsystems.back.extendable.position == Lift.ZERO }
)