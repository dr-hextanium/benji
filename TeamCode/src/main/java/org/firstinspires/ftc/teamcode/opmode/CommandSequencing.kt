package org.firstinspires.ftc.teamcode.opmode

import com.arcrobotics.ftclib.command.CommandScheduler
import com.arcrobotics.ftclib.command.ParallelCommandGroup
import com.arcrobotics.ftclib.command.button.GamepadButton
import com.arcrobotics.ftclib.command.button.Trigger
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.arcrobotics.ftclib.gamepad.TriggerReader
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.command.core.DepositBar
import org.firstinspires.ftc.teamcode.command.core.DepositBasket
import org.firstinspires.ftc.teamcode.command.core.Grab
import org.firstinspires.ftc.teamcode.command.core.NudgeLift
import org.firstinspires.ftc.teamcode.command.core.OpenClaw
import org.firstinspires.ftc.teamcode.command.core.ToIntake
import org.firstinspires.ftc.teamcode.command.core.Transfer
import org.firstinspires.ftc.teamcode.command.core.VariableElbow
import org.firstinspires.ftc.teamcode.command.core.VariableTwist2
import org.firstinspires.ftc.teamcode.command.core.VariableWrist
import org.firstinspires.ftc.teamcode.command.core.ZeroTwist
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow
import org.firstinspires.ftc.teamcode.hardware.subsystems.Extendo
import org.firstinspires.ftc.teamcode.hardware.subsystems.Lift
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist

@TeleOp
class CommandSequencing : BasedOpMode() {
    val lift by lazy {
        Robot.Subsystems.back.extendable as Lift
    }

    val extendo by lazy {
        Robot.Subsystems.front.extendable as Extendo
    }

    override fun initialize() {
        val front = Robot.Subsystems.front
        val back = Robot.Subsystems.back
        val gamepad = Robot.gamepad1

        CommandScheduler.getInstance().schedule(
            OpenClaw(back.grabber),
            OpenClaw(front.grabber),
//            VariableWrist(Wrist.BACK_DEFAULT, back.grabber.wrist),
//            VariableElbow(Elbow.BACK_TO_DEPOSIT, back.grabber.elbow),
            VariableWrist(Wrist.FRONT_DEFAULT, front.grabber.wrist),
            VariableElbow(Elbow.FRONT_DEFAULT, front.grabber.elbow)
        )
        CommandScheduler.getInstance().run()

        GamepadButton(gamepad, CROSS).whenPressed(ToIntake())
        GamepadButton(gamepad, CIRCLE).whenPressed(Grab())
        GamepadButton(gamepad, TRIANGLE).whenPressed(Transfer())
        GamepadButton(gamepad, SQUARE).whenPressed(DepositBasket(lift))
        GamepadButton(gamepad, GamepadKeys.Button.DPAD_UP).whenPressed(DepositBar(lift))

        GamepadButton(Robot.gamepad1, GamepadKeys.Button.LEFT_BUMPER).whenPressed(NudgeLift(-2, lift))
        GamepadButton(Robot.gamepad1, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(NudgeLift(2, lift))

        Trigger { TriggerReader(Robot.gamepad1, GamepadKeys.Trigger.RIGHT_TRIGGER).isDown }
            .whileActiveContinuous(VariableTwist2(0.03, 10))

        Trigger { TriggerReader(Robot.gamepad1, GamepadKeys.Trigger.LEFT_TRIGGER).isDown }
            .whileActiveContinuous(VariableTwist2(-0.03,10))

        Trigger { TriggerReader(Robot.gamepad1, GamepadKeys.Trigger.LEFT_TRIGGER).isDown && TriggerReader(Robot.gamepad1, GamepadKeys.Trigger.RIGHT_TRIGGER).isDown }
            .whenActive(
                ParallelCommandGroup(
                    ZeroTwist(Robot.Subsystems.front.grabber),
                    ZeroTwist(Robot.Subsystems.back.grabber)
                )
            )
    }

    override fun cycle() {  }

    companion object {
        val CROSS = GamepadKeys.Button.A
        val CIRCLE = GamepadKeys.Button.B
        val TRIANGLE = GamepadKeys.Button.Y
        val SQUARE = GamepadKeys.Button.X
    }
}