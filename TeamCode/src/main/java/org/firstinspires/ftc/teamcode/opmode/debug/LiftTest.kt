package org.firstinspires.ftc.teamcode.opmode.debug

import com.arcrobotics.ftclib.command.button.GamepadButton
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.command.core.DepositBar
import org.firstinspires.ftc.teamcode.command.core.DepositBasket
import org.firstinspires.ftc.teamcode.command.core.LiftTo
import org.firstinspires.ftc.teamcode.command.core.NudgeLift
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.subsystems.Lift
import org.firstinspires.ftc.teamcode.opmode.BasedOpMode

@TeleOp
class LiftTest : BasedOpMode() {
    val lift by lazy { Robot.Subsystems.back.extendable as Lift }

    override fun initialize() {
        GamepadButton(Robot.gamepad1, GamepadKeys.Button.X).whenPressed(DepositBasket(lift))
        GamepadButton(Robot.gamepad1, GamepadKeys.Button.Y).whenPressed(DepositBar(lift))
//        GamepadButton(Robot.gamepad1, GamepadKeys.Button.A).whenPressed(LiftTo(Lift.State.ZERO, lift))
        GamepadButton(Robot.gamepad1, GamepadKeys.Button.LEFT_BUMPER).whenPressed(NudgeLift(-2, lift))
        GamepadButton(Robot.gamepad1, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(NudgeLift(2, lift))
    }

    override fun cycle() {
//        if(gamepad1.x) {
//            telemetry.addLine("pressed x")
//        } else if(gamepad1.y) {
//            telemetry.addLine("pressed y")
//        } else if(gamepad1.a) {
//            telemetry.addLine("pressed a")
//        }
//
//        telemetry.addLine("target: ${lift.target}")
//        telemetry.addLine("pos: ${lift.position}")
//        telemetry.addLine("blac: ${lift.blackLift.getCurrent(CurrentUnit.AMPS)}")
//        telemetry.addLine("pinc: ${lift.pinkLift.getCurrent(CurrentUnit.AMPS)}")
//        telemetry.addLine("blac overcurrent: ${lift.blackLift.isOverCurrent}")
//        telemetry.addLine("pinc overcurrent: ${lift.pinkLift.isOverCurrent}")
//        telemetry.addLine("blac current: ${lift.blackLift.getCurrent(CurrentUnit.AMPS)}")
//        telemetry.addLine("pinc current: ${lift.pinkLift.getCurrent(CurrentUnit.AMPS)}")
    }
}
