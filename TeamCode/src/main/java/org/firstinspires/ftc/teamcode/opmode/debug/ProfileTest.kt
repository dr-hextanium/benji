//package org.firstinspires.ftc.teamcode.opmode.debug
//
//import com.acmerobotics.roadrunner.DisplacementProfile
//import com.acmerobotics.roadrunner.constantProfile
//import com.qualcomm.robotcore.eventloop.opmode.OpMode
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp
//import com.qualcomm.robotcore.hardware.Servo
//
//@TeleOp
//class ProfileTest : OpMode() {
//	val profile = constantProfile(
//		1.0,
//		0.1,
//		0.3,
//		-0.15,
//		0.1
//	)
//
//	val servo by lazy {
//		hardwareMap["test"] as Servo
//	}
//
//	override fun init() {
//		servo.position = 0.0
//	}
//
//	override fun loop() {
//		val result = profile.baseProfile[servo.position + 0.001]
//
//		servo.position = result[0]
//
//		telemetry.addData("result", result.toString())
//		telemetry.addData("length", result.size())
//		telemetry.addData("values", result.values())
//	}
//}