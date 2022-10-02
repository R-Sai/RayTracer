package com.labco.raytracer

import com.labco.raytracer.camera.Camera
import com.labco.raytracer.light.Light
import com.labco.raytracer.material.FloatColor

class Scene {
    var objects = mutableListOf<SceneObject>()
    var lights = mutableListOf<Light>()
    var camera = Camera()

    fun addObjects(vararg objects: SceneObject) {
        this.objects.addAll(objects)
    }

    fun addLights(vararg lights: Light) {
        this.lights.addAll(lights)
    }

    fun getObjectIntersection(rayOrigin: Vector3, rayDirection: UnitVector3, excludeObject: SceneObject? = null): Intersection? {
        val intersections = objects.filter { it != excludeObject }.map {
            it.getIntersection(rayOrigin, rayDirection)?.let { intersectionPoint ->
                Intersection(it, intersectionPoint, (intersectionPoint.position - rayOrigin).getLength())
            }
        }
        return intersections.filterNotNull().minByOrNull { it.distanceToIntersectionPoint }
    }

    fun getIntersectionColor(intersection: Intersection, ray: Ray): FloatColor {
        var newRayColor = FloatColor.BLACK
        lights.forEach {
            val intersectionToLightVector = (it.position - intersection.intersectionPoint.position).toUnitVector3()
            val nearestIntersectedObjectDistance = getObjectIntersection(intersection.intersectionPoint.position, intersectionToLightVector, intersection.sceneObject)?.distanceToIntersectionPoint

            if (nearestIntersectedObjectDistance == null || it.getDistanceFrom(intersection.intersectionPoint.position) < nearestIntersectedObjectDistance) {
                newRayColor += it.getDiffuseColor(intersection.intersectionPoint, intersection.sceneObject.material)
                newRayColor += it.getSpecularColor(intersection.intersectionPoint, ray, intersection.sceneObject.material)
            }
        }
        return newRayColor
    }

    data class Intersection(val sceneObject: SceneObject, val intersectionPoint: RayIntersectionPoint, val distanceToIntersectionPoint: Float)
}
