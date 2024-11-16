package org.firstinspires.ftc.teamcode.hardware

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.arcrobotics.ftclib.command.CommandScheduler
import com.arcrobotics.ftclib.command.button.GamepadButton
import com.arcrobotics.ftclib.gamepad.GamepadEx
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.ServoImplEx
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.command.core.CloseClaw
import org.firstinspires.ftc.teamcode.command.core.ElbowPointsDown
import org.firstinspires.ftc.teamcode.command.core.ElbowToTransfer
import org.firstinspires.ftc.teamcode.command.core.WristPointsDown
import org.firstinspires.ftc.teamcode.command.core.WristToTransfer
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
	val scheduler: CommandScheduler
		get() = CommandScheduler.getInstance()

	lateinit var telemetry: MultipleTelemetry
	lateinit var hw: HardwareMap

	lateinit var gamepad1: GamepadEx
	lateinit var gamepad2: GamepadEx

	object Subsystems {
		val front = GrabberSet(false)
		val back  = GrabberSet()

		class GrabberSet(val enabled: Boolean = true) {
			lateinit var grabber: Grabber
			lateinit var claw:    Claw
			lateinit var twist:   Twist
			lateinit var wrist:   Wrist
			lateinit var elbow:   Elbow

//			fun all() = listOf(grabber, claw, twist, wrist, elbow)
			fun all() = listOf(twist) as List<ISubsystem>
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

	fun init(hw: HardwareMap, telemetry: Telemetry, gamepad1: Gamepad, gamepad2: Gamepad) {
		this.telemetry = MultipleTelemetry(FtcDashboard.getInstance().telemetry, telemetry)
		this.hw = hw

		this.gamepad1 = GamepadEx(gamepad1)
		this.gamepad2 = GamepadEx(gamepad2)

//		Motors.fr = CachingDcMotor(hw["frontRight"] as DcMotor)
//		Motors.fl = CachingDcMotor(hw["frontLeft"] as DcMotor)
//		Motors.br = CachingDcMotor(hw["backRight"] as DcMotor)
//		Motors.bl = CachingDcMotor(hw["backLeft"] as DcMotor)

		Servos.frontClaw = hw["frontClaw"] as ServoImplEx
		Servos.frontTwist = hw["frontTwist"] as ServoImplEx
        Servos.frontWrist = hw["frontWrist"] as ServoImplEx
        Servos.frontElbow = hw["frontElbow"] as ServoImplEx

		Subsystems.front.claw = Claw(Servos.frontClaw, Globals.Bounds.Front.claw)
		Subsystems.front.twist = Twist(Servos.frontTwist, Globals.Bounds.Front.twist)
		Subsystems.front.wrist = Wrist(Servos.frontWrist, Globals.Bounds.Front.wrist)
		Subsystems.front.elbow = Elbow(Servos.frontElbow, Globals.Bounds.Front.elbow)

		Subsystems.front.grabber = Grabber(
			Subsystems.front.claw,
			Subsystems.front.twist,
			Subsystems.front.wrist,
			Subsystems.front.elbow
		)

		Servos.backClaw = hw["backClaw"] as ServoImplEx
		Servos.backTwist = hw["backTwist"] as ServoImplEx
		Servos.backWrist = hw["backWrist"] as ServoImplEx
		Servos.backElbow = hw["backElbow"] as ServoImplEx

		Subsystems.back.claw = Claw(Servos.backClaw, Globals.Bounds.Back.claw)
		Subsystems.back.twist = Twist(Servos.backTwist, Globals.Bounds.Back.twist)
		Subsystems.back.wrist = Wrist(Servos.backWrist, Globals.Bounds.Back.wrist)
		Subsystems.back.elbow = Elbow(Servos.backElbow, Globals.Bounds.Back.elbow)

		Subsystems.back.grabber = Grabber(
			Subsystems.back.claw,
			Subsystems.back.twist,
			Subsystems.back.wrist,
			Subsystems.back.elbow
		)

		Subsystems.front.wrist.servo.direction = Servo.Direction.REVERSE
//		Subsystems.back.claw.servo.direction = Servo.Direction.REVERSE

		scheduler.registerSubsystem(*Subsystems.all().toTypedArray())

		reset()
	}

	override fun reset() {
		scheduler.reset()
		Subsystems.all().forEach { it.reset() }
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