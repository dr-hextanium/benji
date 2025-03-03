package org.firstinspires.ftc.teamcode.opmode.debug;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Config
@TeleOp
public class PIDFTuner extends OpMode {
    private PIDController controller;

    //lift
    //kP = 0.4, kI = 0, kD = 0, f = 0.09

    public static double p = 0.0, i = 0.0, d = 0.0;
    public static double f = 0.0;

    public static int target = 0;

    private static final double ticksPerInch = 4670.0;

    private DcMotorEx pinkLift;
    private DcMotorEx blackLift;
    private DcMotorEx liftEncoder;

    private double asInches(int ticks) {
        return ticks / ticksPerInch;
    }

//    private int asTicks(int inches) {
//        return inches * ticksPerInch;
//    }

    @Override
    public void init() {
        controller = new PIDController(p, i, d);

        controller.setTolerance(0.005);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        pinkLift = hardwareMap.get(DcMotorEx.class, "pinkLift");
        blackLift = hardwareMap.get(DcMotorEx.class, "blackLift");
        liftEncoder = hardwareMap.get(DcMotorEx.class, "liftEncoder");

        liftEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        blackLift.setDirection(DcMotorSimple.Direction.REVERSE);
//        liftEncoder.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        controller.setPID(p, i, d);

        int liftPos = (int) asInches(liftEncoder.getCurrentPosition());
        double pid = controller.calculate(liftPos, target);

        double power = pid + f * Math.signum(pid);

        if (!controller.atSetPoint()) {
            pinkLift.setPower(power);
            blackLift.setPower(power);
        } else {
            pinkLift.setPower(0.0);
            blackLift.setPower(0.0);
        }

        telemetry.addData("position ", liftPos);
        telemetry.addData("target ", target);
        telemetry.addData("pink power ", pinkLift.getPower());
        telemetry.addData("black power ", blackLift.getPower());
        telemetry.update();
    }
}