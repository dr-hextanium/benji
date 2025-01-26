package org.firstinspires.ftc.teamcode.hardware.subsystems

import android.graphics.Color
import android.util.Log
import android.util.Size
import com.acmerobotics.dashboard.config.Config
import com.arcrobotics.ftclib.command.SubsystemBase
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.util.SortOrder
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl
import org.firstinspires.ftc.teamcode.hardware.Alliance
import org.firstinspires.ftc.teamcode.hardware.Globals
import org.firstinspires.ftc.teamcode.hardware.vision.ColorBlobLocatorProcessorMulti
import org.firstinspires.ftc.teamcode.hardware.vision.ColorSensorProcessor
import org.firstinspires.ftc.teamcode.hardware.vision.ColorRange
import org.firstinspires.ftc.teamcode.hardware.vision.ImageRegion
import org.firstinspires.ftc.vision.VisionPortal
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor
import org.firstinspires.ftc.vision.opencv.ColorSpace
import org.opencv.core.Point
import org.opencv.core.Rect
import org.opencv.core.Scalar
import java.util.concurrent.TimeUnit
import java.util.function.BooleanSupplier

@Config
class NewCameraSubsystem(val name: CameraName) : ISubsystem {
    var colorLocator: ColorBlobLocatorProcessorMulti = ColorBlobLocatorProcessorMulti(
        ColorRange(ColorSpace.HSV, Scalar(13.0, 60.0, 60.0), Scalar(50.0, 255.0, 255.0)),
        ImageRegion.asImageCoordinates(40, 170, 310, 200),
        ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY,
        -1,
        -1,
        false,
        -1,
        Color.rgb(255, 120, 31),
        Color.rgb(255, 255, 255),
        Color.rgb(3, 227, 252)
    )

    lateinit var colorSensor: ColorSensorProcessor
    lateinit var portal: VisionPortal

    var color = ColorSensorProcessor.ColorType.NONE

    var pixelPos = 0.0
    var isYellow = false
    var sampleAngle = 0.0


    override fun reset() {
        // add yellow colors (same for all alliances)
        colorLocator.addColors(
            ColorRange(
                ColorSpace.HSV, minimumYellow, maximumYellow
            )
        )

        when (Globals.ALLIANCE) {
            Alliance.Red -> {
                colorLocator.addColors(
                    ColorRange(
                        ColorSpace.HSV, minimumRed1, maximumRed1
                    )
                )
                colorLocator.addColors(
                    ColorRange(
                        ColorSpace.HSV, minimumRed2, maximumRed2
                    )
                )
            }

            Alliance.Blue -> colorLocator.addColors(
                ColorRange(
                    ColorSpace.HSV, minimumBlue, maximumBlue
                )
            )
        }

        colorSensor =
            ColorSensorProcessor(Rect(Point(120.0, 25.0), Point(200.0, 105.0)), { true })

        portal = VisionPortal.Builder().addProcessors(colorLocator, colorSensor)
            .setCameraResolution(Size(320, 240)).setCamera(name).enableLiveView(true).build()

        setEnabled(true)
    }

    override fun read() {

    }

    override fun update() {
        pixelPos = 0.0
        isYellow = false

        if (portal.getProcessorEnabled(colorLocator)) {
            val blobs = colorLocator.blobs

            ColorBlobLocatorProcessor.Util.filterByArea(
                minContourArea.toDouble(), 20000.0, blobs
            )
            val dist = 10000
            ColorBlobLocatorProcessor.Util.sortByArea(SortOrder.DESCENDING, blobs)

            if (blobs.isNotEmpty()) {/*for (int i = 0; i < Math.min(blobs.size(), 3); i++) {
                    if (Math.abs(160 - blobs.get(i).getBoxFit().center.x) < Math.abs(dist)) {
                        dist = (int) (160 - blobs.get(i).getBoxFit().center.x);
                    }
                }
                pixelPos = dist;*/
                pixelPos = (160 - blobs[0].boxFit.center.x).toInt().toDouble()
                sampleAngle = blobs[0].boxFit.angle
            }
        }
        if (portal.getProcessorEnabled(colorSensor)) {
            color = colorSensor.detection
            isYellow = color == ColorSensorProcessor.ColorType.YELLOW
            Log.v("camera", "color: " + color)
            // Log.i("camera", "yellow: " + yellow);
        }
    }

    override fun write() {
    }

    fun setEnabled(enable: Boolean) {
        portal.setProcessorEnabled(colorLocator, enable)
        portal.setProcessorEnabled(colorSensor, enable)
    }

    /**
     * @return whether the set was successful or not
     */
    fun setExposure(exposure: Int): Boolean {
        if (portal.cameraState != VisionPortal.CameraState.STREAMING) {
            return false
        }

        val control = portal.getCameraControl(ExposureControl::class.java)
        control.setMode(ExposureControl.Mode.Manual)
        Log.i("camera", "exposure: " + control.getExposure(TimeUnit.MILLISECONDS))
        return control.setExposure(exposure.toLong(), TimeUnit.MILLISECONDS)
    }

    fun setExposure(): Boolean {
        return setExposure(exposureMillis)
    }

    fun setOnlyYellow(onlyYellow: Boolean) {
        colorLocator.onlyFirstColor = onlyYellow
    }

    @JvmOverloads
    fun waitForSetExposure(
        timeoutMs: Long, maxAttempts: Int, exposure: Int = exposureMillis
    ): Boolean {
        val startMs = System.currentTimeMillis()
        var attempts = 0
        var msAfterStart: Long = 0
        while (msAfterStart < timeoutMs && attempts++ < maxAttempts) {
            Log.i(
                "camera", String.format(
                    "Attempting to set camera exposure, attempt %d, %d ms after start",
                    attempts,
                    msAfterStart
                )
            )
            if (setExposure(exposure)) {
                Log.i("camera", "Set exposure succeeded")
                return true
            }
            msAfterStart = System.currentTimeMillis() - startMs
        }

        Log.e("camera", "Set exposure failed")
        return false
    }

    fun saveFrame(name: String?) {
        portal.saveNextFrameRaw(name)
    }

    companion object {
        var minimumRed1: Scalar = Scalar(0.0, 125.0, 50.0)
        var maximumRed1: Scalar = Scalar(12.0, 255.0, 255.0)

        var minimumRed2: Scalar = Scalar(168.0, 125.0, 50.0)
        var maximumRed2: Scalar = Scalar(180.0, 255.0, 255.0)

        var minimumBlue: Scalar = Scalar(100.0, 125.0, 50.0)
        var maximumBlue: Scalar = Scalar(140.0, 255.0, 255.0)

        var minimumYellow: Scalar = Scalar(13.0, 60.0, 60.0)
        var maximumYellow: Scalar = Scalar(50.0, 255.0, 255.0)

        var exposureMillis: Int = 65
        var minContourArea: Int = 200
    }
}
