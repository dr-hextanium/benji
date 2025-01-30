package org.firstinspires.ftc.teamcode.utility

val Int.deg
    get() = Math.toRadians(this.toDouble())

val Double.deg
    get() = Math.toRadians(this)
