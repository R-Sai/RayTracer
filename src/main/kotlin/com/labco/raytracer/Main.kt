package com.labco.raytracer

import com.labco.raytracer.light.PointLight
import com.labco.raytracer.material.FloatColor
import com.labco.raytracer.material.Material
import com.labco.raytracer.primitives.Sphere
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


private val rayTracer = RayTracer()
private val sphereMaterial1 = Material(FloatColor(0.5f, 0.5f, 1f), 10f, 1f, 0f)
private val sphereMaterial2 = Material(FloatColor(0.5f, 1f, 0.5f), 10f, 1f, 0f)
private val sphereMaterial3 = Material(FloatColor(1f, 0.5f, 0.5f), 10f, 1f, 0f)
private val sphereMaterial4 = Material(FloatColor(1f, 1f, 1f), 1f, 0f, 0f)
private val sphere1 = Sphere(Vector3(1.5f, 0f, 4f), 1f, sphereMaterial1)
private val sphere2 = Sphere(Vector3(-4.5f, 0f, 4f), 1f, sphereMaterial2)
private val sphere3 = Sphere(Vector3(3f, 0f, 10f), 5f, sphereMaterial3)
private val sphere4 = Sphere(Vector3(3f, -100000f, 0f), 99993f, sphereMaterial4)
private val pointLight1 = PointLight(Vector3(-10f, 1f, -4f), FloatColor(1f, 1f, 1f))
private val pointLight2 = PointLight(Vector3(10f, 0.5f, -4f), FloatColor(1f, 1f, 1f))
private val pointLight3 = PointLight(Vector3(0f, 10f, 7f), FloatColor(1f, 1f, 1f))
private val scene = Scene().apply {
    addObjects(sphere1, sphere3, sphere4)
    addLights(pointLight1, pointLight2)
}

private fun <T> Array<Array<T>>.flip() {
    this.forEach { it.reverse() }
    this.reverse()
}

fun main() {

    val startTime = System.nanoTime()
    val bitmap = rayTracer.renderScene(scene, 6)
    println("Draw time: ${(System.nanoTime() - startTime) / 1e+9} s")

    val bufferedImage = BufferedImage(bitmap.size, bitmap.first().size, BufferedImage.TYPE_INT_RGB)
    bitmap.flip()
    bitmap.forEachIndexed { xIndex, x ->
        x.forEachIndexed { yIndex, color ->
            bufferedImage.setRGB(xIndex, yIndex, Color(color.r, color.g, color.b, 1f).rgb)
        }
    }
    val outputfile = File("image.jpg")
    ImageIO.write(bufferedImage, "jpg", outputfile)
    Runtime.getRuntime().exec("open image.jpg")
}