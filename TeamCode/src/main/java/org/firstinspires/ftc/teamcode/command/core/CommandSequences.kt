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
import org.firstinspires.ftc.teamcode.hardware.subsystems.Twist
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist

class ToIntake : SequentialCommandGroup(
    ParallelCommandGroup(
        WristToDefault(Robot.Subsystems.front.grabber),
        ElbowToDefault(Robot.Subsystems.front.grabber),
        VariableWrist(Wrist.BACK_DEFAULT, Robot.Subsystems.back.wrist),
        VariableElbow(Elbow.BACK_TO_DEPOSIT, Robot.Subsystems.back.elbow),
        OpenClaw(Robot.Subsystems.front.grabber),
        ExtendoTo(Extendo.TO_INTAKE, Robot.Subsystems.front.extendable as Extendo),
        LiftTo(Lift.ZERO, Robot.Subsystems.back.extendable as Lift),
        ZeroTwist(Robot.Subsystems.front.grabber),
    ),
    WaitCommand(100),
    ParallelCommandGroup(
        VariableWrist(Wrist.FRONT_INTERMEDIATE, Robot.Subsystems.front.wrist),
        VariableElbow(Elbow.FRONT_INTERMEDIATE, Robot.Subsystems.front.elbow)
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

class ChangeArm : ConditionalCommand(
    ParallelCommandGroup(
        VariableWrist(Wrist.FRONT_INTERMEDIATE, Robot.Subsystems.front.grabber.wrist),
        VariableElbow(Elbow.FRONT_INTERMEDIATE, Robot.Subsystems.front.grabber.elbow)
    ),
    ParallelCommandGroup(
        WristToDefault(Robot.Subsystems.front.grabber),
        ElbowToDefault(Robot.Subsystems.front.grabber)
    ),
    { Robot.Subsystems.front.grabber.elbow.position == Elbow.FRONT_DEFAULT ||
            Robot.Subsystems.front.grabber.elbow.position == Elbow.FRONT_TO_TRANSFER }
)

class Intake(extendo: Extendo) : ConditionalCommand(
    SequentialCommandGroup(
        OpenClaw(Robot.Subsystems.front.grabber, 100),
        ParallelCommandGroup(
            WristPointsDown(Robot.Subsystems.front.grabber),
            ElbowPointsDown(Robot.Subsystems.front.grabber)
        ),
        WaitCommand(500),
        CloseClaw(Robot.Subsystems.front.grabber),
        WaitCommand(300),
        ParallelCommandGroup(
            WristToTransfer(Robot.Subsystems.front.grabber),
            ElbowToTransfer(Robot.Subsystems.front.grabber)
        )
    ),
    OpenClaw(Robot.Subsystems.back.grabber),
    { extendo.state == Extendo.State.INTAKING }
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

//class Transfer: SequentialCommandGroup(
//    ParallelCommandGroup(
//        LiftTo(Lift.State.ZERO, Robot.Subsystems.back.extendable as Lift),
//        VariableWrist(Wrist.BACK_DEFAULT, Robot.Subsystems.back.grabber.wrist),
//        VariableElbow(Elbow.BACK_TO_DEPOSIT, Robot.Subsystems.back.grabber.elbow),
//        OpenClaw(Robot.Subsystems.back.grabber),
//        ZeroTwist(Robot.Subsystems.front.grabber),
//        ZeroTwist(Robot.Subsystems.back.grabber)
//    ),
//
//    WaitCommand(100),
//
//    ParallelCommandGroup(
//        VariableWrist(Wrist.FRONT_INTERMEDIATE, Robot.Subsystems.front.grabber.wrist),
//        VariableElbow(Elbow.FRONT_INTERMEDIATE, Robot.Subsystems.front.grabber.elbow),
//        NudgeLift(-2, Robot.Subsystems.back.extendable as Lift)
//    ),
//
//    WaitCommand(500),
//    ExtendoTo(Extendo.TO_TRANSFER, Robot.Subsystems.front.extendable as Extendo, 100),
//
//    WaitCommand(500),
//
//    ParallelCommandGroup(
//        VariableWrist(Wrist.FRONT_TO_TRANSFER, Robot.Subsystems.front.grabber.wrist),
//        VariableElbow(Elbow.FRONT_TO_TRANSFER, Robot.Subsystems.front.grabber.elbow),
//    ),
//
//    WaitCommand(500),
//
//    ParallelCommandGroup(
//        VariableWrist(Wrist.BACK_TO_TRANSFER, Robot.Subsystems.back.grabber.wrist),
//        VariableElbow(Elbow.BACK_TO_TRANSFER, Robot.Subsystems.back.grabber.elbow),
//    ),
//
//    WaitCommand(500),
//
//    CloseClaw(Robot.Subsystems.back.grabber, 300),
//    OpenClaw(Robot.Subsystems.front.grabber, 100),
//
//    ParallelCommandGroup(
//        NudgeLift(2, Robot.Subsystems.back.extendable as Lift),
//        ElbowToDefault(Robot.Subsystems.front.grabber),
//        WristToDefault(Robot.Subsystems.front.grabber),
//        ExtendoTo(0, Robot.Subsystems.front.extendable as Extendo)
//    ),
//
//    ParallelCommandGroup(
//        VariableWrist(Wrist.BACK_TO_DEPOSIT, Robot.Subsystems.back.grabber.wrist),
//        VariableElbow(Elbow.BACK_TO_DEPOSIT, Robot.Subsystems.back.grabber.elbow)
//    ),
//
//    WaitCommand(500),
//    CloseClaw(Robot.Subsystems.front.grabber)
//)

class Transfer: SequentialCommandGroup(
    ParallelCommandGroup(
        LiftTo(Lift.State.ZERO, Robot.Subsystems.back.extendable as Lift),
        VariableWrist(Wrist.BACK_TO_TRANSFER, Robot.Subsystems.back.grabber.wrist),
        VariableElbow(Elbow.BACK_TO_TRANSFER, Robot.Subsystems.back.grabber.elbow),
        OpenClaw(Robot.Subsystems.back.grabber),
        VariableTwist(Twist.MIDDLE, Robot.Subsystems.front.grabber),
        VariableTwist(Twist.MIDDLE, Robot.Subsystems.back.grabber)
    ),

    WaitCommand(100),

    ParallelCommandGroup(
        WristToTransfer(Robot.Subsystems.front.grabber),
        ElbowToTransfer(Robot.Subsystems.front.grabber),
        NudgeLift(-2, Robot.Subsystems.back.extendable as Lift)
    ),

    WaitCommand(300),
    ExtendoTo(Extendo.TO_TRANSFER, Robot.Subsystems.front.extendable as Extendo, 500),

    CloseClaw(Robot.Subsystems.back.grabber, 300),
    OpenClaw(Robot.Subsystems.front.grabber, 100),

    ParallelCommandGroup(
        NudgeLift(2, Robot.Subsystems.back.extendable as Lift),
        ElbowToDefault(Robot.Subsystems.front.grabber),
        WristToDefault(Robot.Subsystems.front.grabber),
        ExtendoTo(0, Robot.Subsystems.front.extendable as Extendo)
    ),

    ParallelCommandGroup(
        VariableWrist(Wrist.BACK_DEFAULT, Robot.Subsystems.back.grabber.wrist),
        VariableElbow(Elbow.BACK_TO_DEPOSIT, Robot.Subsystems.back.grabber.elbow),
        CloseClaw(Robot.Subsystems.front.grabber)
    ),

    WaitCommand(200)
)

//class DepositBasket(lift: Lift): ConditionalCommand(
//    SequentialCommandGroup(
//        LiftTo(Lift.State.HIGH_BASKET, lift, 100),
//        VariableWrist(Wrist.BACK_TO_DEPOSIT, Robot.Subsystems.back.wrist)
//    ),
//    SequentialCommandGroup(
//        LiftTo(Lift.State.ZERO, lift),
//        VariableWrist(Wrist.BACK_TO_TRANSFER, Robot.Subsystems.back.grabber.wrist),
//        VariableElbow(Elbow.BACK_TO_TRANSFER, Robot.Subsystems.back.grabber.elbow),
//        OpenClaw(Robot.Subsystems.back.grabber)
//    ),
//    { Robot.Subsystems.back.extendable.position == Lift.ZERO }
//)
//
//class DepositBar(lift: Lift): ConditionalCommand(
//    SequentialCommandGroup(
//        LiftTo(Lift.State.HIGH_BAR, lift, 100),
//        VariableWrist(Wrist.BACK_TO_DEPOSIT, Robot.Subsystems.back.wrist)
//    ),
//    SequentialCommandGroup(
//        LiftTo(Lift.State.ZERO, lift),
//        VariableWrist(Wrist.BACK_TO_TRANSFER, Robot.Subsystems.back.grabber.wrist),
//        VariableElbow(Elbow.BACK_TO_TRANSFER, Robot.Subsystems.back.grabber.elbow),
//        OpenClaw(Robot.Subsystems.back.grabber)
//    ),
//    { Robot.Subsystems.back.extendable.position == Lift.ZERO }
//)

class DepositBasket(lift: Lift): ConditionalCommand(
    SequentialCommandGroup(
        LiftTo(Lift.State.HIGH_BASKET, lift, 100),
        ParallelCommandGroup(
            VariableWrist(Wrist.BACK_TO_DEPOSIT, Robot.Subsystems.back.wrist),
            VariableElbow(Elbow.BACK_TO_DEPOSIT, Robot.Subsystems.back.elbow),
            VariableTwist(Twist.MIDDLE, Robot.Subsystems.back.grabber)
        )
    ),
    SequentialCommandGroup(
        ParallelCommandGroup(
            VariableWrist(Wrist.BACK_DEFAULT, Robot.Subsystems.back.wrist),
            VariableElbow(Elbow.BACK_TO_TRANSFER, Robot.Subsystems.back.elbow),
            ZeroTwist(Robot.Subsystems.back.grabber)
        ),
        WaitCommand(100),
        LiftTo(Lift.State.ZERO, lift)
    ),
    { lift.state == Lift.State.ZERO }
)

class DepositBar(lift: Lift): ConditionalCommand(
    SequentialCommandGroup(
        ZeroTwist(Robot.Subsystems.back.grabber),
        LiftTo(Lift.State.HIGH_BAR, lift, 100),
        ParallelCommandGroup(
            VariableWrist(Wrist.BACK_TO_DEPOSIT, Robot.Subsystems.back.wrist),
            VariableElbow(Elbow.BACK_TO_DEPOSIT, Robot.Subsystems.back.elbow)
        )
    ),
    SequentialCommandGroup(
        ZeroTwist(Robot.Subsystems.back.grabber),
        OpenClaw(Robot.Subsystems.back.grabber),
        ParallelCommandGroup(
            VariableWrist(Wrist.BACK_TO_TRANSFER, Robot.Subsystems.back.wrist),
            VariableElbow(Elbow.BACK_TO_TRANSFER, Robot.Subsystems.back.elbow)
        ),
        WaitCommand(100),
        LiftTo(Lift.State.ZERO, lift)
    ),
    { lift.state == Lift.State.ZERO }
)