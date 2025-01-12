package org.firstinspires.ftc.teamcode.pedro.constants;

import com.pedropathing.localization.constants.*;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Arrays;

public class LConstants {
    static {
        OTOSConstants.useCorrectedOTOSClass = true;
        OTOSConstants.hardwareMapName = "otos";

        OTOSConstants.linearUnit = DistanceUnit.INCH;
        OTOSConstants.angleUnit = AngleUnit.RADIANS;

        OTOSConstants.offset = new SparkFunOTOS.Pose2D(0, 0, 2 * Math.PI - Math.PI / 2);

        double distance = 24.0 * 4;

        OTOSConstants.linearScalar = distance / (92.151 + 91.889 + 91.817 + 92.081 + 92.129) * 5.0;
        OTOSConstants.angularScalar = (0.9963 + 0.9705 + 0.9814 + 0.9895 + 0.9831) / 5.0;
    }
}




