package org.firstinspires.ftc.teamcode.opmode.debug

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.hardware.Robot
import org.firstinspires.ftc.teamcode.hardware.drive.swerve.SwerveModule
import org.firstinspires.ftc.teamcode.hardware.drive.swerve.SwerveModule.ModuleConfig
import org.firstinspires.ftc.teamcode.opmode.BasedOpMode
import org.firstinspires.ftc.teamcode.opmode.debug.ModuleFeedForwardFinder.Phase.*

@TeleOp(group = "Debug")
class ModuleFeedForwardFinder : BasedOpMode() {
    val module by lazy {
        SwerveModule(
            Robot.Motors.fl,
            Robot.Servos.fl,
            Robot.AnalogEncoders.fl
        )
    }

    var phase = Sampling
    var sample = 0

    var power = 0.0

    var readings = mutableListOf<Double>()
    var steadyNoise = 0.0

    override fun initialize() {
        module.reset()

        ModuleConfig.P = 0.0
        ModuleConfig.I = 0.0
        ModuleConfig.D = 0.0
        ModuleConfig.F = 0.0
    }

    override fun loop() {
        Robot.update()

        module.read()
        module.update()

        when (phase) {
            Sampling -> {
                if (sample < SAMPLING_LOOPS) {
                    readings.add(module.position)
                    sample++
                } else {
                    sample = 0
                    phase = Computing
                }
            }

            Computing -> {
                steadyNoise = readings
                    .zipWithNext { a, b -> b - a }
                    .average()
            }

            Iterating -> {

            }
        }

        module.write()
    }

    enum class Phase {
        Sampling,
        Computing,
        Iterating
    }

    companion object {
        const val SAMPLING_LOOPS = 1000
        const val POWER_STEP = 0.001
    }
}