package com.labco.raytracer

import com.labco.raytracer.material.FloatColor
import kotlinx.coroutines.*

class RayTracer {

    fun renderScene(scene: Scene, samples: Int): Array<Array<FloatColor>> {
        val rays = scene.camera.generateRays(samples)
        val rayBatches = rays.chunked(rays.size / 10)
        runBlocking {
            rayBatches.forEach { batch ->
                launch(Dispatchers.IO) {
                    batch.forEach { ray ->
                        scene.camera.sensor.pixels[ray.sensorX][ray.sensorY] += castRay(ray, scene, 5) / samples
                    }
                }
            }
        }
        return scene.camera.sensor.pixels
    }

    private fun castRay(ray: Ray, scene: Scene, reflectionCount: Int, color: FloatColor = FloatColor.BLACK, excludeObject: SceneObject? = null): FloatColor {
        if (reflectionCount == 0) return color
        val closestIntersection = scene.getObjectIntersection(ray.origin, ray.direction, excludeObject)
        if (closestIntersection != null) {
            val reflectedRay = closestIntersection.sceneObject.reflect(ray, closestIntersection.intersectionPoint)
            val passColor = color + (scene.getIntersectionColor(closestIntersection, reflectedRay) * ray.energy)
            return if(reflectedRay.energy <= 0f) {
                passColor
            } else {
                castRay(reflectedRay, scene, reflectionCount - 1, passColor, closestIntersection.sceneObject)
            }
        }
        return color
    }
}