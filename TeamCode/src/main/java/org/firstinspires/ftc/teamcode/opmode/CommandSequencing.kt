package org.firstinspires.ftc.teamcode.opmode

import com.arcrobotics.ftclib.command.CommandScheduler
import com.arcrobotics.ftclib.command.ParallelCommandGroup
import com.arcrobotics.ftclib.command.button.GamepadButton
import com.arcrobotics.ftclib.command.button.Trigger
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.arcrobotics.ftclib.gamepad.TriggerReader
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.teamcode.command.auto.DepositToBar
import org.firstinspires.ftc.teamcode.command.core.ChangeArm
import org.firstinspires.ftc.teamcode.command.core.DepositBar
import org.firstinspires.ftc.teamcode.command.core.DepositBasket
import org.firstinspires.ftc.teamcode.command.core.ExtendoTo
import org.firstinspires.ftc.teamcode.command.core.Grab
import org.firstinspires.ftc.teamcode.command.core.Intake
import org.firstinspires.ftc.teamcode.command.core.NudgeLift
import org.firstinspires.ftc.teamcode.command.core.OpenClaw
import org.firstinspires.ftc.teamcode.command.core.ToIntake
import org.firstinspires.ftc.teamcode.command.core.Transfer
import org.firstinspires.ftc.teamcode.command.core.VariableElbow
import org.firstinspires.ftc.teamcode.command.core.NudgeTwist
import org.firstinspires.ftc.teamcode.command.core.VariableWrist
import org.firstinspires.ftc.teamcode.command.core.ZeroTwist
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow
import org.firstinspires.ftc.teamcode.hardware.subsystems.Extendo
import org.firstinspires.ftc.teamcode.hardware.subsystems.Lift
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist

@TeleOp
class CommandSequencing : BasedOpMode() {
    val extendo by lazy { Robot.Subsystems.front.extendable as Extendo  }
    val lift by lazy { Robot.Subsystems.back.extendable as Lift }

    override fun initialize() {
        val front = Robot.Subsystems.front
        val back = Robot.Subsystems.back
        val gamepad1 = Robot.gamepad1
        val gamepad2 = Robot.gamepad2

        CommandScheduler.getInstance().schedule(
            OpenClaw(back.grabber),
            OpenClaw(front.grabber),
            VariableWrist(Wrist.BACK_DEFAULT, back.grabber.wrist),
            VariableElbow(Elbow.BACK_TO_DEPOSIT, back.grabber.elbow),
            VariableWrist(Wrist.FRONT_DEFAULT, front.grabber.wrist),
            VariableElbow(Elbow.FRONT_DEFAULT, front.grabber.elbow)
        )

        CommandScheduler.getInstance().run()


        //SAMPLES: GAMEPAD 1
        GamepadButton(gamepad1, SQUARE).whenPressed(ToIntake())
        GamepadButton(gamepad1, TRIANGLE).whenPressed(Intake(extendo))
        GamepadButton(gamepad1, CIRCLE).whenPressed(Transfer())
        GamepadButton(gamepad1, CROSS).whenPressed(DepositBasket(lift))
        GamepadButton(gamepad1, GamepadKeys.Button.DPAD_DOWN).whenPressed(ChangeArm())
        GamepadButton(gamepad1, GamepadKeys.Button.DPAD_UP).whenPressed(Grab())

        GamepadButton(Robot.gamepad1, GamepadKeys.Button.LEFT_BUMPER).whenPressed(
            NudgeLift(
                -2,
                lift
            )
        )
        GamepadButton(Robot.gamepad1, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
            NudgeLift(
                2,
                lift
            )
        )

        Trigger { TriggerReader(Robot.gamepad1, GamepadKeys.Trigger.RIGHT_TRIGGER).isDown }
            .whileActiveContinuous(NudgeTwist(0.16, 500))

        Trigger { TriggerReader(Robot.gamepad1, GamepadKeys.Trigger.LEFT_TRIGGER).isDown }
            .whileActiveContinuous(NudgeTwist(-0.16, 500))

        Trigger {
            TriggerReader(
                Robot.gamepad1,
                GamepadKeys.Trigger.LEFT_TRIGGER
            ).isDown && TriggerReader(Robot.gamepad1, GamepadKeys.Trigger.RIGHT_TRIGGER).isDown
        }
            .whenActive(
                ParallelCommandGroup(
                    ZeroTwist(Robot.Subsystems.front.grabber),
                    ZeroTwist(Robot.Subsystems.back.grabber)
                )
            )


        //SPECIMENS: GAMEPAD 2
        GamepadButton(gamepad2, SQUARE).whenPressed(ToIntake())
        GamepadButton(gamepad2, TRIANGLE).whenPressed(Intake(extendo))
        GamepadButton(gamepad2, CIRCLE).whenPressed(Transfer())
        GamepadButton(gamepad2, CROSS).whenPressed(DepositToBar())
        GamepadButton(gamepad2, GamepadKeys.Button.DPAD_DOWN).whenPressed(ChangeArm())
        GamepadButton(gamepad2, GamepadKeys.Button.DPAD_UP).whenPressed(Grab())
        GamepadButton(gamepad2, GamepadKeys.Button.DPAD_LEFT).whenPressed(ExtendoTo(0, extendo))

//        GamepadButton(gamepad, SQUARE).whenPressed(
//            ExtendoTo(Extendo.TO_TRANSFER, Robot.Subsystems.front.extendable as Extendo, 500)
//        )
//
//        GamepadButton(gamepad, CIRCLE).whenPressed(
//            ExtendoTo(Extendo.TO_INTAKE, Robot.Subsystems.front.extendable as Extendo, 500)
//        )

        GamepadButton(Robot.gamepad2, GamepadKeys.Button.LEFT_BUMPER).whenPressed(
            NudgeLift(
                -2,
                lift
            )
        )
        GamepadButton(Robot.gamepad2, GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
            NudgeLift(
                2,
                lift
            )
        )

//        Trigger { TriggerReader(Robot.gamepad1, GamepadKeys.Trigger.RIGHT_TRIGGER).isDown }
//            .whileActiveContinuous(VariableTwist2(0.03, 10))
//
//        Trigger { TriggerReader(Robot.gamepad1, GamepadKeys.Trigger.LEFT_TRIGGER).isDown }
//            .whileActiveContinuous(VariableTwist2(-0.03,10))

        Trigger { TriggerReader(Robot.gamepad2, GamepadKeys.Trigger.RIGHT_TRIGGER).isDown }
            .whileActiveContinuous(NudgeTwist(0.16, 500))

        Trigger { TriggerReader(Robot.gamepad2, GamepadKeys.Trigger.LEFT_TRIGGER).isDown }
            .whileActiveContinuous(NudgeTwist(-0.16, 500))

        Trigger {
            TriggerReader(
                Robot.gamepad2,
                GamepadKeys.Trigger.LEFT_TRIGGER
            ).isDown && TriggerReader(Robot.gamepad2, GamepadKeys.Trigger.RIGHT_TRIGGER).isDown
        }
            .whenActive(
                ParallelCommandGroup(
                    ZeroTwist(Robot.Subsystems.front.grabber),
                    ZeroTwist(Robot.Subsystems.back.grabber)
                )
            )
    }

    override fun cycle() {
        telemetry.addData("total current", Robot.Motors.all().sumOf { it.getCurrent(CurrentUnit.AMPS) })
    }

    companion object {
        val CROSS = GamepadKeys.Button.A
        val CIRCLE = GamepadKeys.Button.B
        val TRIANGLE = GamepadKeys.Button.Y
        val SQUARE = GamepadKeys.Button.X
    }
}