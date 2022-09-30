package com.labco.raytracer

import com.labco.raytracer.material.FloatColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RayTracer {

    fun renderScene(scene: Scene, samples: Int): Array<Array<FloatColor>> {
        runBlocking {
            val rays = scene.camera.generateRays(samples)
            println("Number of rays ${rays.size}")
            val rayBatches = rays.chunked(rays.size / 10)
            rayBatches.forEach { batch ->
                launch(Dispatchers.IO) {
                    val iterator = batch.toMutableList().listIterator()
                    while (iterator.hasNext()) {

                        val ray = iterator.next()
                        ray.let {
                            val closestIntersection = scene.getObjectIntersection(it.origin, it.direction)
                            if (closestIntersection != null) {
                                val reflectedRay =
                                    closestIntersection.sceneObject.reflect(it, closestIntersection.intersectionPoint)
                                reflectedRay.color = scene.getColorFromLights(closestIntersection, reflectedRay)
                                scene.camera.sensor.pixels[it.sensorX][it.sensorY] += reflectedRay.color / samples
                            }
                            //                iterator.set(reflectedRay)
                        }
                    }
                }
            }
        }
        return scene.camera.sensor.pixels
    }
}