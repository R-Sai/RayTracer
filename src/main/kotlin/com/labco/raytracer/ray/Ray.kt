package com.labco.raytracer.ray

import com.labco.raytracer.common.UnitVector3
import com.labco.raytracer.common.Vector3
import com.labco.raytracer.primitives.Sphere
import com.labco.raytracer.times
import kotlin.math.pow
import kotlin.math.sqrt

data class Ray(
    var origin: Vector3,
    var direction: UnitVector3,
    val sensorX: Int,
    val sensorY: Int,
    var energy: Float = 1f
) {

    fun getIntersection(sphere: Sphere): RayIntersectionPoint? {
        val sphereToRayOrigin = this.origin - sphere.position
        val b = 2 * this.direction dot sphereToRayOrigin
        val c = sphereToRayOrigin.dot(sphereToRayOrigin) - sphere.radius.pow(2)

        if (b.pow(2) - 4 * c <= 0f) {
            return null
        }
        val p = (-b - sqrt((b * b) - 4f * c)) / (2f)
        val reflectionPoint = this.origin + (this.direction * p)
        val normal = (reflectionPoint - sphere.position).toUnitVector3()
        return RayIntersectionPoint(reflectionPoint, normal)
    }
}
