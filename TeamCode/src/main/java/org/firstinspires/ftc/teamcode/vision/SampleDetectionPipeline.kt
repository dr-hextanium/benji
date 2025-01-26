package org.firstinspires.ftc.teamcode.vision

import android.graphics.Canvas
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration
import org.firstinspires.ftc.vision.VisionProcessor
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.MatOfPoint2f
import org.opencv.core.Point
import org.opencv.core.RotatedRect
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import java.util.Collections
import kotlin.concurrent.Volatile

class SampleDetectionPipeline : VisionProcessor {
    /*
     * Working image buffers
     */
    var ycrcbMat: Mat = Mat()
    var crMat: Mat = Mat()
    var cbMat: Mat = Mat()

    var blueThresholdMat: Mat = Mat()
    var redThresholdMat: Mat = Mat()
    var yellowThresholdMat: Mat = Mat()


    var morphedBlueThreshold: Mat = Mat()
    var morphedRedThreshold: Mat = Mat()
    var morphedYellowThreshold: Mat = Mat()

    var contoursOnPlainImageMat: Mat = Mat()

    /*
     * Elements for noise reduction
     */
    var erodeElement: Mat = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, Size(3.5, 3.5))
    var dilateElement: Mat = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, Size(3.5, 3.5))

    override fun init(width: Int, height: Int, calibration: CameraCalibration) {
    }

    class AnalyzedStone {
        var angle: Double = 0.0
        var color: String? = null
        var point: Point? = null
    }

    var internalStoneList: ArrayList<AnalyzedStone> = ArrayList()

    @Volatile
    var detectedStones: ArrayList<AnalyzedStone> = ArrayList()

    /*
    * Viewport stages
    */
    enum class Stage {
        FINAL,
        YCrCb,
        MASKS,
        MASKS_NR,
        CONTOURS
    }

    var stages: Array<Stage> = Stage.entries.toTypedArray()
    var stageNum: Int = 0

    override fun onDrawFrame(
        canvas: Canvas,
        onscreenWidth: Int,
        onscreenHeight: Int,
        scaleBmpPxToCanvasPx: Float,
        scaleCanvasDensity: Float,
        userContext: Any
    ) {
        var nextStageNum = stageNum + 1

        if (nextStageNum >= stages.size) {
            nextStageNum = 0
        }

        stageNum = nextStageNum
    }

    override fun processFrame(input: Mat, captureTimeNanos: Long): Any {
        internalStoneList.clear()

        /*
         * Run the image processing
         */
        findContours(input)

        detectedStones = ArrayList(internalStoneList)

        /*
         * Decide which buffer to send to the viewport
         */
        when (stages[stageNum]) {
            Stage.YCrCb -> {
                return ycrcbMat
            }

            Stage.FINAL -> {
                return input
            }

            Stage.MASKS -> {
                val masks = Mat()
                Core.addWeighted(yellowThresholdMat, 1.0, redThresholdMat, 1.0, 0.0, masks)
                Core.addWeighted(masks, 1.0, blueThresholdMat, 1.0, 0.0, masks)
                return masks
            }

            Stage.MASKS_NR -> {
                val masksNR = Mat()
                Core.addWeighted(
                    morphedYellowThreshold,
                    1.0,
                    morphedRedThreshold,
                    1.0,
                    0.0,
                    masksNR
                )
                Core.addWeighted(masksNR, 1.0, morphedBlueThreshold, 1.0, 0.0, masksNR)
                return masksNR
            }

            Stage.CONTOURS -> {
                return contoursOnPlainImageMat
            }

            else -> {
                return input
            }
        }
    }

    fun findContours(input: Mat) {
        // Convert the input image to YCrCb color space
        Imgproc.cvtColor(input, ycrcbMat, Imgproc.COLOR_RGB2YCrCb)

        // Extract the Cb and Cr channels
        Core.extractChannel(ycrcbMat, cbMat, 2) // Cb channel index is 2
        Core.extractChannel(ycrcbMat, crMat, 1) // Cr channel index is 1

        // Threshold the channels to form masks
        Imgproc.threshold(
            cbMat,
            blueThresholdMat,
            BLUE_MASK_THRESHOLD.toDouble(),
            255.0,
            Imgproc.THRESH_BINARY
        )
        Imgproc.threshold(
            crMat,
            redThresholdMat,
            RED_MASK_THRESHOLD.toDouble(),
            255.0,
            Imgproc.THRESH_BINARY
        )
        Imgproc.threshold(
            cbMat,
            yellowThresholdMat,
            YELLOW_MASK_THRESHOLD.toDouble(),
            255.0,
            Imgproc.THRESH_BINARY_INV
        )

        // Apply morphology to the masks
        morphMask(blueThresholdMat, morphedBlueThreshold)
        morphMask(redThresholdMat, morphedRedThreshold)
        morphMask(yellowThresholdMat, morphedYellowThreshold)

        // Find contours in the masks
        var blueContoursList = ArrayList<MatOfPoint>()
        Imgproc.findContours(
            morphedBlueThreshold,
            blueContoursList,
            Mat(),
            Imgproc.RETR_EXTERNAL,
            Imgproc.CHAIN_APPROX_NONE
        )

        var redContoursList = ArrayList<MatOfPoint>()
        Imgproc.findContours(
            morphedRedThreshold,
            redContoursList,
            Mat(),
            Imgproc.RETR_EXTERNAL,
            Imgproc.CHAIN_APPROX_NONE
        )

        var yellowContoursList = ArrayList<MatOfPoint>()
        Imgproc.findContours(
            morphedYellowThreshold,
            yellowContoursList,
            Mat(),
            Imgproc.RETR_EXTERNAL,
            Imgproc.CHAIN_APPROX_NONE
        )

        // Create a plain image for drawing contours
        contoursOnPlainImageMat = Mat.zeros(input.size(), input.type())

        blueContoursList = blueContoursList
//            .filter { Imgproc.boundingRect(it).area() in 100.0..1000.0 }
            .sortedByDescending { Imgproc.boundingRect(it).area() }
            .take(2).let { ArrayList(it) }

        redContoursList = redContoursList
//            .filter { Imgproc.boundingRect(it).area() in 100.0..1000.0 }
            .sortedByDescending { Imgproc.boundingRect(it).area() }
            .take(2).let { ArrayList(it) }

        yellowContoursList = yellowContoursList
//            .filter { Imgproc.boundingRect(it).area() in 100.0..1000.0 }
            .sortedByDescending { Imgproc.boundingRect(it).area() }
            .take(2).let { ArrayList(it) }


        if (blueContoursList.size > 2) {
            blueContoursList = ArrayList(blueContoursList.subList(0, 2))
        }

        if (redContoursList.size > 2) {
            redContoursList = ArrayList(redContoursList.subList(0, 2))
        }

        if (yellowContoursList.size > 2) {
            yellowContoursList = ArrayList(yellowContoursList.subList(0, 2))
        }

        // Analyze and draw contours
        for (contour in blueContoursList) {
            analyzeContour(contour, input, "Blue")
        }

        for (contour in redContoursList) {
            analyzeContour(contour, input, "Red")
        }

        for (contour in yellowContoursList) {
            analyzeContour(contour, input, "Yellow")
        }
    }

    fun morphMask(input: Mat, output: Mat) {
        /*
         * Apply erosion and dilation for noise reduction
         */
        Imgproc.erode(input, output, erodeElement)
        Imgproc.erode(output, output, erodeElement)

        Imgproc.dilate(output, output, dilateElement)
        Imgproc.dilate(output, output, dilateElement)
    }

    fun analyzeContour(contour: MatOfPoint, input: Mat, color: String) {
        // Transform the contour to a different format
        val points = contour.toArray()
        val contour2f = MatOfPoint2f(*points)

        // Fit a rotated rectangle to the contour and draw it
        val rotatedRectFitToContour = Imgproc.minAreaRect(contour2f)
        drawRotatedRect(rotatedRectFitToContour, input, color)
        drawRotatedRect(rotatedRectFitToContour, contoursOnPlainImageMat, color)

        val point = rotatedRectFitToContour.center

        // Adjust the angle based on rectangle dimensions
        var rotRectAngle = rotatedRectFitToContour.angle
        if (rotatedRectFitToContour.size.width < rotatedRectFitToContour.size.height) {
            rotRectAngle += 90.0
        }

        // Compute the angle and store it
        val angle = -(rotRectAngle - 180)
        drawTagText(
            rotatedRectFitToContour,
            Math.round(angle).toInt().toString() + " deg",
            input,
            color
        )

        // Store the detected stone information
        val analyzedStone = AnalyzedStone()
        analyzedStone.angle = rotRectAngle
        analyzedStone.color = color
        analyzedStone.point = point

        internalStoneList.add(analyzedStone)
    }

    companion object {
        /*
     * Threshold values
     */
        const val YELLOW_MASK_THRESHOLD: Int = 0
        const val BLUE_MASK_THRESHOLD: Int = 90
        const val RED_MASK_THRESHOLD: Int = 200

        /*
     * Colors
     */
        val RED: Scalar = Scalar(255.0, 0.0, 0.0)
        val BLUE: Scalar = Scalar(0.0, 0.0, 255.0)
        val YELLOW: Scalar = Scalar(255.0, 255.0, 0.0)

        const val CONTOUR_LINE_THICKNESS: Int = 2

        fun drawTagText(rect: RotatedRect, text: String?, mat: Mat, color: String) {
            val colorScalar = getColorScalar(color)

            Imgproc.putText(
                mat,  // The buffer we're drawing on
                text,  // The text we're drawing
                Point( // The anchor point for the text
                    rect.center.x - 50,  // x anchor point
                    rect.center.y + 25
                ),  // y anchor point
                Imgproc.FONT_HERSHEY_PLAIN,  // Font
                1.0,  // Font size
                colorScalar,  // Font color
                1
            ) // Font thickness
        }

        fun drawRotatedRect(rect: RotatedRect, drawOn: Mat, color: String) {
            /*
         * Draws a rotated rectangle by drawing each of the 4 lines individually
         */
            val points = arrayOfNulls<Point>(4)
            rect.points(points)

            val colorScalar = getColorScalar(color)

            for (i in 0..3) {
                Imgproc.line(drawOn, points[i], points[(i + 1) % 4], colorScalar, 2)
            }
        }

        fun getColorScalar(color: String): Scalar {
            return when (color) {
                "Blue" -> BLUE
                "Yellow" -> YELLOW
                else -> RED
            }
        }
    }
}
