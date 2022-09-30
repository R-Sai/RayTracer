package com.labco.raytracer.primitives

import com.labco.raytracer.*
import com.labco.raytracer.material.Material
import kotlin.math.pow
import kotlin.math.sqrt

data class Sphere(var position: Vector3, var radius: Float, override var material: Material) : SceneObject {

    override fun reflect(ray: Ray, intersectionPoint: RayIntersectionPoint): Ray {
        val reflectionDirection = (2 * (intersectionPoint.normal dot -ray.direction) * intersectionPoint.normal + ray.direction).toUnitVector3()
        return ray.copy(
            origin = intersectionPoint.position,
            direction = reflectionDirection
        )
    }

    override fun getIntersection(rayOrigin: Vector3, rayDirection: UnitVector3): RayIntersectionPoint? {
        val sphereToRayOrigin = rayOrigin - this.position
        val b = 2 * rayDirection dot sphereToRayOrigin
        val c = sphereToRayOrigin.dot(sphereToRayOrigin) - this.radius.pow(2)

        if (b.pow(2) - 4 * c <= 0f) {
            return null
        }

        val length = (-b - sqrt((b * b) - 4f * c)) / (2f)

        val reflectionPoint = rayOrigin + (rayDirection * length)
        val normal = (reflectionPoint - this.position).toUnitVector3()
        return RayIntersectionPoint(reflectionPoint, normal)
    }
}
