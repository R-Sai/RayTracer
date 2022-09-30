package com.labco.raytracer

import com.labco.raytracer.material.Material

interface SceneObject {
    val material: Material
    fun reflect(ray: Ray, intersectionPoint: RayIntersectionPoint): Ray
    fun getIntersection(rayOrigin: Vector3, rayDirection: UnitVector3): RayIntersectionPoint?
}