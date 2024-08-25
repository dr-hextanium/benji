package org.firstinspires.ftc.teamcode.opmode.debug

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.drive.swerve.SwerveModule
import org.firstinspires.ftc.teamcode.opmode.BasedOpMode
import org.firstinspires.ftc.teamcode.utility.geometry.Angle.deg
import org.firstinspires.ftc.teamcode.utility.geometry.Angle.rad
import org.firstinspires.ftc.teamcode.utility.geometry.Vector2D.Extensions.left_stick_vector
import kotlin.math.PI

@TeleOp(group = "Debug")
class ManualSwerveModule : BasedOpMode() {
    val module by lazy { SwerveModule(Robot.Motors.fl, Robot.Servos.fl, Robot.AnalogEncoders.fl) }

    override fun initialize() { module.reset() }

    override fun loop() {
        val vector = gamepad1.left_stick_vector.rotate(-PI / 4.0)

        val magnitude = vector.magnitude * 0.7

        val angle = if (magnitude > 0.3) {
            vector.theta
        } else {
            0.0
        }

        telemetry.addData("position", module.position.rad)
        telemetry.addData("target", module.target.rad)
        telemetry.addData("error", module.error.rad)
        telemetry.addData("flipped", module.flipped)

        module.target = angle
        module.setDrivePower(magnitude)

        Robot.update()

        module.read()
        module.update()
        module.write()
    }
}