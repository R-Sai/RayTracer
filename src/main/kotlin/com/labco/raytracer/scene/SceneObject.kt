package com.labco.raytracer.scene

import com.labco.raytracer.ray.Ray
import com.labco.raytracer.ray.RayIntersectionPoint
import com.labco.raytracer.common.UnitVector3
import com.labco.raytracer.common.Vector3
import com.labco.raytracer.material.Material

interface SceneObject {
    val material: Material
    fun reflect(ray: Ray, intersectionPoint: RayIntersectionPoint): Ray
    fun getIntersection(rayOrigin: Vector3, rayDirection: UnitVector3): RayIntersectionPoint?
}