package com.nipunru.nsfwdetector

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.automl.FirebaseAutoMLLocalModel
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceAutoMLImageLabelerOptions

const val TAG = "NSFWDetector"

object NSFWDetector {
    private const val LABEL_SFW = "nude"
    private const val LABEL_NSFW = "nonnude"
    private const val CONFIDENCE_THRESHOLD: Float = 0.7F

    private val localModel = FirebaseAutoMLLocalModel.Builder()
        .setAssetFilePath("automl/manifest.json")
        .build()

    private val options =
        FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder(localModel).build()
    private val interpreter = FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(options)

    /**
     * This function return weather the bitmap is NSFW or not
     * @param bitmap: Bitmap Image
     * @param confidenceThreshold: Float 0 to 1 (Default is 0.7)
     * @return callback with Boolean and Bitmap
     */
    fun isNSFW(
        bitmap: Bitmap,
        confidenceThreshold: Float = CONFIDENCE_THRESHOLD,
        callback: (Boolean, Bitmap) -> Unit
    ) {
        var threshold = confidenceThreshold

        if (threshold < 0 && threshold > 1) {
            threshold = CONFIDENCE_THRESHOLD
        }
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        interpreter.processImage(image).addOnSuccessListener { labels ->
            try {
                val label = labels[0]
                when (label.text) {
                    LABEL_SFW -> {
                        if (label.confidence > threshold) {
                            callback(true, bitmap)
                        } else {
                            callback(false, bitmap)
                        }
                    }
                    LABEL_NSFW -> {
                        if (label.confidence < (1 - threshold)) {
                            callback(true, bitmap)
                        } else {
                            callback(false, bitmap)
                        }
                    }
                    else -> {
                        callback(false, bitmap)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, e.localizedMessage ?: "NSFW Scan Error")
                callback(false, bitmap)
            }
        }.addOnFailureListener { e ->
            Log.e(TAG, e.localizedMessage ?: "NSFW Scan Error")
            callback(false, bitmap)
        }
    }
}