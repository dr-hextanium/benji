package org.firstinspires.ftc.teamcode.hardware.subsystems

import android.graphics.Color
import android.util.Log
import android.util.Size
import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.util.SortOrder
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl
import org.firstinspires.ftc.teamcode.hardware.Alliance
import org.firstinspires.ftc.teamcode.hardware.Globals.ALLIANCE
import org.firstinspires.ftc.teamcode.hardware.vision.ColorBlobLocatorProcessorMulti
import org.firstinspires.ftc.teamcode.hardware.vision.ColorRange
import org.firstinspires.ftc.teamcode.hardware.vision.ColorSensorProcessor
import org.firstinspires.ftc.teamcode.hardware.vision.ColorSensorProcessor.ColorType
import org.firstinspires.ftc.teamcode.hardware.vision.ImageRegion
import org.firstinspires.ftc.teamcode.vision.SampleDetectionPipeline
import org.firstinspires.ftc.teamcode.vision.SampleDetectionPipeline.AnalyzedStone
import org.firstinspires.ftc.vision.VisionPortal
import org.firstinspires.ftc.vision.VisionProcessor
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor
import org.firstinspires.ftc.vision.opencv.ColorSpace
import org.opencv.core.Point
import org.opencv.core.Rect
import org.opencv.core.Scalar
import java.util.concurrent.TimeUnit

@Config
class CameraSubsystem(val name: CameraName) : ISubsystem {
    lateinit var portal: VisionPortal
    lateinit var processor: ColorBlobLocatorProcessorMulti

//    var detected = ArrayList<AnalyzedStone>()

    override fun reset() {
//        processor = ColorBlobLocatorProcessorMulti()

        portal = VisionPortal.Builder()
            .setCamera(name)
            .addProcessor(processor)
            .build()

        portal.resumeStreaming()
        portal.resumeLiveView()
    }

    override fun read() {
//        detected = processor.detectedStones
    }

    override fun update() {

    }

    override fun write() {

    }
}
