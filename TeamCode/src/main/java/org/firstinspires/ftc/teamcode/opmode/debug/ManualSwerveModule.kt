package org.firstinspires.ftc.teamcode.opmode.debug

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.drive.swerve.SwerveModule
import org.firstinspires.ftc.teamcode.opmode.BasedOpMode
import org.firstinspires.ftc.teamcode.utility.geometry.Vector2D.Extensions.left_stick_vector

@TeleOp(group = "Debug")
class ManualSwerveModule : BasedOpMode() {
    val module by lazy { SwerveModule(Robot.Motors.fl, Robot.Servos.fl, Robot.AnalogEncoders.fl) }

    override fun init() { module.reset() }

    override fun loop() {
        val angle = gamepad1.left_stick_vector.theta
        val magnitude = gamepad1.left_stick_vector.magnitude

        module.target = angle
        module.setDrivePower(magnitude)

        module.read()
        module.update()
        module.write()
    }
}