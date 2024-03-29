package com.labco.raytracer.camera

import com.labco.raytracer.common.Vector3
import com.labco.raytracer.ray.Ray
import com.labco.raytracer.common.UnitVector3
import com.labco.raytracer.millimeterToMeter

class Camera(
    var position: Vector3 = Vector3(0f, 0f, 0f),
    var lookAt: Vector3 = Vector3(0f, 0f, 10f),
    var upDirection: UnitVector3 = UnitVector3(0f, 1f, 0f),
    var focalLength: Float = 28f.millimeterToMeter(),
    var sensor: Sensor = Sensor(48f.millimeterToMeter(), 36f.millimeterToMeter(), 800, 600)
) {

    fun generateRays(samples: Int): ArrayList<Ray> {
        val lookDirection = (lookAt - position).toUnitVector3()
        val leftVector = lookDirection * upDirection
        val upVector = leftVector * lookDirection
        val sensorCenterTopPosition = position + (upVector * (sensor.height / 2f))
        val rayDeltaX = -leftVector * (sensor.width / sensor.resolutionX.toFloat())
        val rayDeltaY = -upVector * (sensor.height / sensor.resolutionY.toFloat())
        val focalPointPosition = position + (lookDirection * focalLength)
        var rayPosition = sensorCenterTopPosition + (leftVector * (sensor.width / 2))
        val maxJiggle = (sensor.height / sensor.resolutionY.toFloat()) / 2f

        val rays = arrayListOf<Ray>()
        for (y in 0 until sensor.resolutionY) {
            val leftRayPosition = rayPosition
            for (x in 0 until sensor.resolutionX) {
                if (samples > 1) {
                    repeat(samples) {
                        val sampleRay = rayPosition.jiggle(maxJiggle)
                        rays.add(
                            Ray(
                                origin = sampleRay,
                                direction = (focalPointPosition - sampleRay).toUnitVector3(),
                                sensorX = x,
                                sensorY = y
                            )
                        )
                    }
                } else {
                    rays.add(
                        Ray(
                            origin = rayPosition,
                            direction = (focalPointPosition - rayPosition).toUnitVector3(),
                            sensorX = x,
                            sensorY = y
                        )
                    )
                }
                rayPosition += rayDeltaX
            }
            rayPosition = leftRayPosition
            rayPosition += rayDeltaY
        }
        return rays
    }
}