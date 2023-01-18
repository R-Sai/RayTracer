package com.labco.raytracer.light

import com.labco.raytracer.ray.Ray
import com.labco.raytracer.ray.RayIntersectionPoint
import com.labco.raytracer.common.Vector3
import com.labco.raytracer.material.FloatColor
import com.labco.raytracer.material.Material

interface Light {
    val position: Vector3
    fun getDiffuseColor(intersectionPoint: RayIntersectionPoint, material: Material): FloatColor
    fun getSpecularColor(intersectionPoint: RayIntersectionPoint, reflectedRay: Ray, material: Material): FloatColor
    fun getDistanceFrom(position: Vector3): Float
}