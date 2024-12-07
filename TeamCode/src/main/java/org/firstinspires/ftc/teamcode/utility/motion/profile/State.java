package org.firstinspires.ftc.teamcode.utility.motion.profile;

import androidx.annotation.NonNull;

public class State {
    public double x = 0;
    public double v = 0;
    public double a = 0;

    public State (double x, double v, double a) {
        this.x = x;
        this.v = v;
        this.a = a;
    }

    public State() {}

    @NonNull
    @Override
    public String toString() {
        return "x: " + x + ", v: " + v + ", a: " + a;
    }
}