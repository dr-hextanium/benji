package org.firstinspires.ftc.teamcode.opmode.debug

import com.arcrobotics.ftclib.command.ParallelCommandGroup
import com.arcrobotics.ftclib.command.button.GamepadButton
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.command.core.ElbowToTransfer
import org.firstinspires.ftc.teamcode.command.core.ExtendoTo
import org.firstinspires.ftc.teamcode.command.core.LiftTo
import org.firstinspires.ftc.teamcode.command.core.WristToTransfer
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.subsystems.Extendo
import org.firstinspires.ftc.teamcode.hardware.subsystems.Lift
import org.firstinspires.ftc.teamcode.opmode.BasedOpMode
import org.firstinspires.ftc.teamcode.opmode.CommandSequencing

@TeleOp
class   ExtendoTest : BasedOpMode() {
    val extendo by lazy { Robot.Subsystems.front.extendable as Extendo }

    override fun initialize() {
        GamepadButton(Robot.gamepad1, CommandSequencing.SQUARE).whenPressed(ExtendoTo(Extendo.TO_INTAKE, Robot.Subsystems.front.extendable as Extendo))
        GamepadButton(Robot.gamepad1, CommandSequencing.TRIANGLE).whenPressed(ExtendoTo(Extendo.TO_TRANSFER, Robot.Subsystems.front.extendable as Extendo))
        GamepadButton(Robot.gamepad1, CommandSequencing.CIRCLE).whenPressed(
            ParallelCommandGroup(
                WristToTransfer(Robot.Subsystems.front.grabber),
                ElbowToTransfer(Robot.Subsystems.front.grabber))
            )
    }

    override fun cycle() {
        telemetry.addLine("target: ${extendo.target}")
        telemetry.addLine("position: ${-extendo.position}")
    }
}