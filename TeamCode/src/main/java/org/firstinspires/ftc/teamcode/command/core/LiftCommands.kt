package org.firstinspires.ftc.teamcode.command.core

import com.arcrobotics.ftclib.command.CommandBase
import com.arcrobotics.ftclib.command.ConditionalCommand
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow
import org.firstinspires.ftc.teamcode.hardware.subsystems.Lift
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist
import org.firstinspires.ftc.teamcode.utility.DelayedCommand

//class LiftTo(position: Int, lift: Lift, delay: Long = 0) : DelayedCommand({ lift.target = position }, delay) {
//    constructor(position: () -> Int, lift: Lift, delay: Long = 0): this(position(), lift,  delay)
//}

class ZeroLift(private val lift: Lift) : CommandBase() {
    var lastPosition = 0
    var finished = false

    override fun initialize() {
        lift.target = Lift.ZERO - 1
    }

    override fun execute() {
        val (pink, black) = Pair(
            lift.pinkLift.getCurrent(CurrentUnit.AMPS) >= 3.5,
            lift.blackLift.getCurrent(CurrentUnit.AMPS) >= 3.5,
        )

        val position = lift.position

        Robot.telemetry.addData("same as last pos?", position == lastPosition)
        Robot.telemetry.addData("position non-positive", position <= 0)
        Robot.telemetry.addData("pink or black", (pink || black))

        if ((position == lastPosition || position <= 0) && (pink || black)) {
            lift.reset()
            finished = true
        }

        lastPosition = position

        Robot.telemetry.addData("pink and black", "$pink and $black")
        Robot.telemetry.addData("last pos", "$lastPosition")
        Robot.telemetry.addData("now pos", "$position")
    }

    override fun isFinished(): Boolean {
        return finished
    }
}

class ZeroLiftButItWorks(private val lift: Lift) : CommandBase() {
    var finished = false

    override fun initialize() {
        lift.target = Lift.ZERO - 1
    }

    override fun execute() {
        if(lift.pinkLift.isOverCurrent && lift.blackLift.isOverCurrent) {
            lift.blackLift.power = 0.0
            lift.pinkLift.power = 0.0

            lift.encoder.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER

            lift.target = 0

            finished = true
        }
    }

    override fun isFinished(): Boolean {
        return finished
    }
}

class LiftTo(state: Lift.State, lift: Lift, delay: Long = 0): DelayedCommand({ lift.state = state }, delay)
class NudgeLift(nudgeBy: Int, lift: Lift, delay: Long = 0): DelayedCommand({ lift.target = lift.position + nudgeBy }, delay)

class LiftToBasket(lift: Lift, delay: Long = 0): ConditionalCommand(
    SequentialCommandGroup(
        LiftTo(Lift.State.HIGH_BASKET, lift),
        VariableWrist(Wrist.BACK_TO_DEPOSIT, Robot.Subsystems.back.wrist)
    ),
    SequentialCommandGroup(
        VariableWrist(Wrist.BACK_TO_TRANSFER, Robot.Subsystems.back.wrist),
        VariableElbow(Elbow.BACK_TO_TRANSFER, Robot.Subsystems.back.elbow, 100),
        LiftTo(Lift.State.ZERO, lift)
    ),
    { lift.state == Lift.State.ZERO }
)

class LiftToBar(lift: Lift, delay: Long = 0): ConditionalCommand(
    SequentialCommandGroup(
        LiftTo(Lift.State.HIGH_BAR, lift),
        VariableWrist(Wrist.BACK_TO_DEPOSIT, Robot.Subsystems.back.wrist)
    ),
    SequentialCommandGroup(
        OpenClaw(Robot.Subsystems.back.grabber, 100),
        VariableWrist(Wrist.BACK_TO_TRANSFER, Robot.Subsystems.back.wrist),
        VariableElbow(Elbow.BACK_TO_TRANSFER, Robot.Subsystems.back.elbow, 100),
        LiftTo(Lift.State.ZERO, lift),
    ),
    { lift.state == Lift.State.ZERO }
)