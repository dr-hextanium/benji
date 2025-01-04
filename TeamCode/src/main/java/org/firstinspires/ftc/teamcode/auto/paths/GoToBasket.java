package org.firstinspires.ftc.teamcode.auto.paths;

import org.firstinspires.ftc.teamcode.auto.pathGeneration.BezierCurve;
import org.firstinspires.ftc.teamcode.auto.pathGeneration.PathBuilder;
import org.firstinspires.ftc.teamcode.auto.pathGeneration.Point;

public class GoToBasket {

    public GoToBasket() {
        PathBuilder builder = new PathBuilder();

        builder
                .addPath(
                        // Line 1
                        new BezierCurve(
                                new Point(8.250, 115.000, Point.CARTESIAN),
                                new Point(20.644, 118.449, Point.CARTESIAN),
                                new Point(15.229, 128.263, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-45));
    }
}

