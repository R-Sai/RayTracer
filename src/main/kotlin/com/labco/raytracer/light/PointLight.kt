package com.labco.raytracer.light

import com.labco.raytracer.Ray
import com.labco.raytracer.RayIntersectionPoint
import com.labco.raytracer.Vector3
import com.labco.raytracer.material.FloatColor
import com.labco.raytracer.material.Material
import com.labco.raytracer.times
import kotlin.math.pow

data class PointLight(
    override val position: Vector3,
    val color: FloatColor
) : Light {

    override fun getDiffuseColor(intersectionPoint: RayIntersectionPoint, material: Material): FloatColor {
        val pointToLightVector = (this.position - intersectionPoint.position).toUnitVector3()
        val coefficient =  (pointToLightVector dot intersectionPoint.normal).coerceAtLeast(0f)
        return if (coefficient > 0) (material.color * color * (1 - material.reflectiveness) * coefficient).toGamma() else FloatColor.BLACK
    }

//    fun getSpecularReflection(intersectionPoint: RayIntersectionPoint, reflectedRay: Ray, material: Material, lookDirection: UnitVector3): FloatColor {
//        val intersectionPointToLightVector = (this.position - intersectionPoint.position).toUnitVector3()
//        val mirrorDirection = (-intersectionPointToLightVector + (2f * (intersectionPoint.normal dot intersectionPointToLightVector) * intersectionPoint.normal))
//        val phong = material.specularCoefficient * (-lookDirection dot mirrorDirection).coerceAtLeast(0f).pow(material.shininess)
//        return phong * (material.specularColor * this.color)
//    }

    override fun getSpecularColor(intersectionPoint: RayIntersectionPoint, reflectedRay: Ray, material: Material): FloatColor {
        val intersectionPointToLightVector = (this.position - intersectionPoint.position).toUnitVector3()
        val specularReflection = reflectedRay.direction.dot(intersectionPointToLightVector).coerceAtLeast(0f)
        if (specularReflection > 0)
            return (specularReflection.pow(material.shininess) * material.specularCoefficient
                    * ((material.color * (1 - material.reflectiveness)) + (this.color * material.reflectiveness))).toGamma()
        return FloatColor.BLACK
    }

    override fun getDistanceFrom(position: Vector3) = (this.position - position).getLength()
}
