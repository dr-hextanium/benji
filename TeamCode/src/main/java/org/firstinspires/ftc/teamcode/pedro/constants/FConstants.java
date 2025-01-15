package org.firstinspires.ftc.teamcode.pedro.constants;

import com.pedropathing.localization.Localizers;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.util.CustomFilteredPIDFCoefficients;
import com.pedropathing.util.CustomPIDFCoefficients;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class FConstants {
    static {
        FollowerConstants.localizers = Localizers.OTOS;

        FollowerConstants.leftFrontMotorName = "frontLeft";
        FollowerConstants.leftRearMotorName = "backLeft";
        FollowerConstants.rightFrontMotorName = "frontRight";
        FollowerConstants.rightRearMotorName = "backRight";

        FollowerConstants.leftFrontMotorDirection = DcMotorSimple.Direction.REVERSE;
        FollowerConstants.leftRearMotorDirection = DcMotorSimple.Direction.REVERSE;
        FollowerConstants.rightFrontMotorDirection = DcMotorSimple.Direction.FORWARD;
        FollowerConstants.rightRearMotorDirection = DcMotorSimple.Direction.FORWARD;

        FollowerConstants.mass = 10.88;

        FollowerConstants.xMovement = 70.5;
        FollowerConstants.yMovement = 49.5;

        FollowerConstants.forwardZeroPowerAcceleration = -36.65;
        FollowerConstants.lateralZeroPowerAcceleration = -72.95;

        FollowerConstants.translationalPIDFCoefficients.setCoefficients(0.1, 0.0, 0.0, 0.0);
        FollowerConstants.translationalPIDFFeedForward = 0.015;
        FollowerConstants.useSecondaryTranslationalPID = true;
        FollowerConstants.translationalPIDFSwitch = 3;
        FollowerConstants.secondaryTranslationalPIDFCoefficients.setCoefficients(0.4, 0.0, 0.01, 0.0); // Not being used, @see useSecondaryTranslationalPID
        FollowerConstants.secondaryTranslationalPIDFFeedForward = 0.015;

        FollowerConstants.headingPIDFCoefficients.setCoefficients(2, 0, 0.0, 0);
        FollowerConstants.headingPIDFFeedForward = 0.01;
        FollowerConstants.useSecondaryHeadingPID = true;
        FollowerConstants.headingPIDFSwitch = 0.15707963267948966;
        FollowerConstants.secondaryHeadingPIDFCoefficients.setCoefficients(0.02, 0, 0.0, 0); // Not being used, @see useSecondaryHeadingPID
        FollowerConstants.secondaryHeadingPIDFFeedForward = 0.012;

        FollowerConstants.drivePIDFCoefficients.setCoefficients(0.01, 0, 0, 0.6, 0);
        FollowerConstants.drivePIDFFeedForward = 0.01;
        FollowerConstants.useSecondaryDrivePID = true;
        FollowerConstants.drivePIDFSwitch = 25;
        FollowerConstants.secondaryDrivePIDFCoefficients.setCoefficients(0.0055, 0, 0, 0.6, 0); // Not being used, @see useSecondaryDrivePID
        FollowerConstants.secondaryDrivePIDFFeedForward = 0.005;

        FollowerConstants.zeroPowerAccelerationMultiplier = 3.5;
        FollowerConstants.centripetalScaling = 0.0005;

        FollowerConstants.pathEndTimeoutConstraint = 500;
        FollowerConstants.pathEndTValueConstraint = 0.995;
        FollowerConstants.pathEndVelocityConstraint = 0.1;
        FollowerConstants.pathEndTranslationalConstraint = 0.1;
        FollowerConstants.pathEndHeadingConstraint = 0.007;
    }
}
