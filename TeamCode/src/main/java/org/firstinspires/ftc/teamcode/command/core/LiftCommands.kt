package org.firstinspires.ftc.teamcode.command.core

import org.firstinspires.ftc.teamcode.hardware.subsystems.Lift
import org.firstinspires.ftc.teamcode.utility.DelayedCommand

//class LiftTo(state: Lift.State, lift: Lift, delay: Long = 0): DelayedCommand({ lift.state = state }, delay)
class LiftTo(target: Int, lift: Lift, delay: Long = 0) : DelayedCommand({ lift.target = target }, delay) {
    constructor(state: Lift.State, lift: Lift, delay: Long = 0): this(state.target, lift,  delay)
}
class NudgeLift(nudgeBy: Int, lift: Lift, delay: Long = 0): DelayedCommand({ lift.target = lift.position + nudgeBy }, delay)