package org.firstinspires.ftc.teamcode.hardware

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.arcrobotics.ftclib.command.CommandScheduler
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.ServoImplEx
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.hardware.devices.CachingDcMotor
import org.firstinspires.ftc.teamcode.hardware.subsystems.Claw
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow
import org.firstinspires.ftc.teamcode.hardware.subsystems.Grabber
import org.firstinspires.ftc.teamcode.hardware.subsystems.ISubsystem
import org.firstinspires.ftc.teamcode.hardware.subsystems.Twist
import org.firstinspires.ftc.teamcode.hardware.subsystems.Wrist

/**
 * Not proud of this, but it does honestly make things easier in the long run.
 *
 * The idea is you have one source to get all of your hardware from, which
 * translates to having less dependency hell with passing down a `HardwareMap`
 * to all receivers.
 */
object Robot : ISubsystem {
	val scheduler by lazy { CommandScheduler.getInstance() }

	lateinit var telemetry: MultipleTelemetry
	lateinit var hw: HardwareMap

	object Subsystems {
		val front = GrabberSet()
		val back  = GrabberSet(false)

		class GrabberSet(val enabled: Boolean = true) {
			lateinit var grabber: Grabber
			lateinit var claw:    Claw
			lateinit var twist:   Twist
			lateinit var wrist:   Wrist
			lateinit var elbow:   Elbow

//			fun all() = listOf(grabber, claw, twist, wrist, elbow)
			fun all() = listOf(twist, claw) as List<ISubsystem>
		}

		fun all(): List<ISubsystem> = listOf(front, back)
			.filter { it.enabled }
			.flatMap { it.all() }
	}

	object Motors {
		lateinit var fr: CachingDcMotor
		lateinit var fl: CachingDcMotor
		lateinit var br: CachingDcMotor
		lateinit var bl: CachingDcMotor

		fun all() = listOf(fr, fl, br, bl)
	}

	object Servos {
		lateinit var frontClaw: ServoImplEx
		lateinit var backClaw: ServoImplEx

		lateinit var frontTwist: ServoImplEx
		lateinit var backTwist: ServoImplEx

		lateinit var frontWrist: ServoImplEx
		lateinit var backWrist: ServoImplEx

		lateinit var frontElbow: ServoImplEx
		lateinit var backElbow: ServoImplEx

		fun all() = listOf(
			frontClaw, backClaw,
			frontTwist, backTwist,
			frontWrist, backWrist,
			frontElbow, backElbow
		)
	}

	fun init(hw: HardwareMap, telemetry: Telemetry) {
		this.telemetry = MultipleTelemetry(FtcDashboard.getInstance().telemetry, telemetry)
		this.hw = hw

//		Motors.fr = CachingDcMotor(hw["front right drive"] as DcMotor, Globals.DRIVE_MOTOR_THRESHOLD)
//		Motors.fl = CachingDcMotor(hw["front left drive"] as DcMotor, Globals.DRIVE_MOTOR_THRESHOLD)
//		Motors.br = CachingDcMotor(hw["back right drive"] as DcMotor, Globals.DRIVE_MOTOR_THRESHOLD)
//		Motors.bl = CachingDcMotor(hw["back left drive"] as DcMotor, Globals.DRIVE_MOTOR_THRESHOLD)

		Servos.frontClaw = hw["frontClaw"] as ServoImplEx
		Servos.frontTwist = hw["frontTwist"] as ServoImplEx
//        Servos.frontWrist = hw["frontWrist"] as ServoImplEx
//        Servos.frontElbow = hw["frontElbow"] as ServoImplEx

		Subsystems.front.claw = Claw(Servos.frontClaw)
		Subsystems.front.twist = Twist(Servos.frontTwist, 0.0)
//		Subsystems.front.wrist = Wrist(Servos.frontWrist)
//		Subsystems.front.elbow = Elbow(Servos.frontElbow)

		scheduler.registerSubsystem(*Subsystems.all().toTypedArray())

		reset()
	}

	override fun reset() {
		scheduler.reset()
		Subsystems.all().forEach { it.reset() }

		Subsystems.front.twist.bound(Globals.Bounds.Front.twist)
		Subsystems.front.claw.bound(Globals.Bounds.Front.claw)
	}

	override fun read() {
		Subsystems.all().forEach { it.read() }
	}

	override fun update() {
		Subsystems.all().forEach { it.update() }
		scheduler.run()
	}

	override fun write() {
		Subsystems.all().forEach { it.write() }
	}
}