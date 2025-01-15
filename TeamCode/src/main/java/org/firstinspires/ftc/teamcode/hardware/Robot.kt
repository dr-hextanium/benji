package org.firstinspires.ftc.teamcode.hardware

//import org.firstinspires.ftc.teamcode.hardware.devices.SparkFunOTOSCorrected
import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.acmerobotics.roadrunner.ftc.SparkFunOTOSCorrected
import com.arcrobotics.ftclib.command.CommandScheduler
import com.arcrobotics.ftclib.gamepad.GamepadEx
import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.VoltageSensor
import com.qualcomm.robotcore.util.ElapsedTime
import dev.frozenmilk.dairy.cachinghardware.CachingDcMotorEx
import dev.frozenmilk.dairy.cachinghardware.CachingServo
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.hardware.drive.mecanum.CachingMecanumDrive
import org.firstinspires.ftc.teamcode.hardware.subsystems.Claw
import org.firstinspires.ftc.teamcode.hardware.subsystems.Elbow
import org.firstinspires.ftc.teamcode.hardware.subsystems.Extendo
import org.firstinspires.ftc.teamcode.hardware.subsystems.Grabber
import org.firstinspires.ftc.teamcode.hardware.subsystems.IExtendable
import org.firstinspires.ftc.teamcode.hardware.subsystems.ISubsystem
import org.firstinspires.ftc.teamcode.hardware.subsystems.Lift
import org.firstinspires.ftc.teamcode.hardware.subsystems.OTOSSubsystem
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

	lateinit var hubs: List<LynxModule>

	lateinit var telemetry: MultipleTelemetry
	lateinit var hw: HardwareMap

	lateinit var gamepad1: GamepadEx
	lateinit var gamepad2: GamepadEx

	lateinit var voltageSensor: Iterator<VoltageSensor>
	var voltageTimer = ElapsedTime()
	var voltage: Double = 0.0

	object Subsystems {
		val front = GrabberSet()
		val back  = GrabberSet()

		lateinit var drive: CachingMecanumDrive
		lateinit var otos: OTOSSubsystem

		class GrabberSet(val enabled: Boolean = true) {
			lateinit var grabber: Grabber
			lateinit var claw:    Claw
			lateinit var twist:   Twist
			lateinit var wrist:   Wrist
			lateinit var elbow:   Elbow

			lateinit var extendable: IExtendable

//			fun all() = listOf(grabber, claw, twist, wrist, elbow)
			// TODO: Add twist back when we figure all that out
			fun all() = listOf(elbow, wrist, claw, twist, extendable)
		}

		fun all(): List<ISubsystem> = listOf(front, back)
			.filter { it.enabled }
			.flatMap { it.all() } + otos
	}

	object Motors {
		lateinit var fr: CachingDcMotorEx
		lateinit var fl: CachingDcMotorEx
		lateinit var br: CachingDcMotorEx
		lateinit var bl: CachingDcMotorEx

		lateinit var extendoMotor: CachingDcMotorEx
		lateinit var pinkLift: CachingDcMotorEx
		lateinit var blackLift: CachingDcMotorEx
		lateinit var liftEncoder: DcMotorEx

		fun all() = listOf(fr, fl, br, bl, extendoMotor, pinkLift, blackLift, liftEncoder)
	}

	object Servos {
		lateinit var frontClaw: CachingServo
		lateinit var backClaw: CachingServo

		lateinit var frontTwist: CachingServo
		lateinit var backTwist: CachingServo

		lateinit var frontWrist: CachingServo
		lateinit var backWrist: CachingServo

		lateinit var frontElbow: CachingServo
		lateinit var backElbow: CachingServo

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

		this.hubs = hw.getAll(LynxModule::class.java)

		this.hubs.forEach { it.bulkCachingMode = LynxModule.BulkCachingMode.MANUAL }

		this.voltageSensor = hw.voltageSensor.iterator()
		this.voltageTimer.reset()

		this.gamepad1 = GamepadEx(gamepad1)
		this.gamepad2 = GamepadEx(gamepad2)

		Motors.fl = CachingDcMotorEx(hw["frontLeft"] as DcMotorEx, 0.0)
		Motors.br = CachingDcMotorEx(hw["backRight"] as DcMotorEx, 0.0)
		Motors.bl = CachingDcMotorEx(hw["backLeft"] as DcMotorEx, 0.0)
		Motors.fr = CachingDcMotorEx(hw["frontRight"] as DcMotorEx, 0.0)

		Subsystems.otos = OTOSSubsystem(hw["otos"] as SparkFunOTOSCorrected)

		Subsystems.drive = CachingMecanumDrive(
			true,
			Motors.fl,
			Motors.fr,
			Motors.bl,
			Motors.br
		)

		Motors.extendoMotor = CachingDcMotorEx(hw["extendo"] as DcMotorEx)
		Motors.pinkLift = CachingDcMotorEx(hw["pinkLift"] as DcMotorEx)
		Motors.blackLift = CachingDcMotorEx(hw["blackLift"] as DcMotorEx)
		Motors.liftEncoder = hw["liftEncoder"] as DcMotorEx

		Servos.frontClaw = CachingServo(hw["frontClaw"] as Servo)
		Servos.frontTwist = CachingServo(hw["frontTwist"] as Servo)
        Servos.frontWrist = CachingServo(hw["frontWrist"] as Servo)
        Servos.frontElbow = CachingServo(hw["frontElbow"] as Servo)

		Subsystems.front.claw = Claw(Servos.frontClaw, Globals.Bounds.Front.claw)
		Subsystems.front.twist = Twist(Servos.frontTwist, Globals.Bounds.Front.twist)
		Subsystems.front.wrist = Wrist(Servos.frontWrist, Globals.Bounds.Front.wrist)
		Subsystems.front.elbow = Elbow(Servos.frontElbow, Globals.Bounds.Front.elbow)

		Subsystems.back.extendable = Lift(Motors.pinkLift, Motors.blackLift, Motors.liftEncoder)
		Subsystems.front.extendable = Extendo(Motors.extendoMotor)

		Subsystems.front.grabber = Grabber(
			Subsystems.front.claw,
			Subsystems.front.twist,
			Subsystems.front.wrist,
			Subsystems.front.elbow
		)

		Servos.backClaw = CachingServo(hw["backClaw"] as Servo)
		Servos.backTwist = CachingServo(hw["backTwist"] as Servo)
		Servos.backWrist = CachingServo(hw["backWrist"] as Servo)
		Servos.backElbow = CachingServo(hw["backElbow"] as Servo)

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
		Subsystems.back.wrist.servo.direction = Servo.Direction.REVERSE
		Subsystems.back.claw.servo.direction = Servo.Direction.REVERSE

		scheduler.registerSubsystem(*Subsystems.all().toTypedArray())

		reset()
	}

	override fun reset() {
		scheduler.reset()
		Subsystems.all().forEach { it.reset() }

		Subsystems.drive.stop()
	}

	override fun read() {
		if (voltageTimer.milliseconds() > 500.0 && voltageSensor.hasNext()) {
			voltage = voltageSensor.next().voltage
		}

		Subsystems.all().forEach { it.read() }
	}

	override fun update() {
		scheduler.run()
		Subsystems.all().forEach { it.update() }
	}

	override fun write() {
		Subsystems.all().forEach { it.write() }
	}
}