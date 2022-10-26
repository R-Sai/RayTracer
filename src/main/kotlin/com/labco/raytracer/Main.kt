package com.labco.raytracer

import com.labco.raytracer.camera.Camera
import com.labco.raytracer.camera.Sensor
import com.labco.raytracer.light.PointLight
import com.labco.raytracer.material.FloatColor
import com.labco.raytracer.material.Material
import com.labco.raytracer.primitives.Sphere
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.max


private val rayTracer = RayTracer()
private val sphereMaterial1 = Material(FloatColor(0.5f, 0.5f, 1f), 10f, 1f, 0.8f)
private val sphereMaterial2 = Material(FloatColor(0.5f, 1f, 0.5f), 10f, 1f, 0.2f)
private val sphereMaterial3 = Material(FloatColor(1f, 0.5f, 0.5f), 10f, 1f, 0.6f)
private val sphereMaterial4 = Material(FloatColor(1f, 1f, 1f), 1f, 0f, 1f)
private val sphere1 = Sphere(Vector3(-1.5f, 0f, 2f), 1f, sphereMaterial1)
private val sphere2 = Sphere(Vector3(0f, 20f, 4f), 12f, sphereMaterial2)
private val sphere3 = Sphere(Vector3(3f, 0f, 10f), 5f, sphereMaterial3)
private val sphere4 = Sphere(Vector3(3f, -100000f, 0f), 99993f, sphereMaterial4)
private val pointLight1 = PointLight(Vector3(-10f, 1f, -4f), FloatColor(1f, 1f, 1f))
private val pointLight2 = PointLight(Vector3(10f, 0.5f, -4f), FloatColor(1f, 1f, 1f))
//private val pointLight3 = PointLight(Vector3(0f, 10f, 7f), FloatColor(1f, 1f, 1f))
private val scene = Scene().apply {
    addObjects(sphere1, sphere2, sphere3, sphere4)
//    addObjects(sphere3)
    addLights(pointLight1, pointLight2)
    camera = Camera(position = Vector3(5f, 10f, -30f), lookAt = Vector3(0f, 0f, 10f), sensor = Sensor(48f.millimeterToMeter(), 36f.millimeterToMeter(), 800 * 4, 600 * 4))
}

private fun <T> Array<Array<T>>.flip() {
    this.forEach { it.reverse() }
    this.reverse()
}

fun main() {

    val startTime = System.nanoTime()
    val bitmap = rayTracer.renderScene(scene, 1)
    println("Draw time: ${(System.nanoTime() - startTime) / 1e+9} s")

    val bufferedImage = BufferedImage(bitmap.size, bitmap.first().size, BufferedImage.TYPE_INT_RGB)
    bitmap.flip()

    bitmap.forEachIndexed { xIndex, x ->
        x.forEachIndexed { yIndex, color ->
//            color.coerce()
            val hdrColor = hdr(color)
            bufferedImage.setRGB(
                xIndex,
                yIndex,
                Color(hdrColor.r, hdrColor.g, hdrColor.b, 1f).rgb
            )
        }
    }
    val outputFile = File("image.jpg")
    ImageIO.write(bufferedImage, "jpg", outputFile)
    Runtime.getRuntime().exec("open image.jpg")
}

fun hdr(color: FloatColor): FloatColor {
    val weightR = 0.124f
    val weightG = 0.68f
    val weightB = 0.195f
    val max = max(max(color.r, color.g), color.b)
    if(max > 1) {
        val l = (color.r * weightR) + (color.g * weightG) + (color.b * weightB)
        return if(l < 1) {
            val t = (1 - l) / (max - l)
            FloatColor((color.r * t) + (l * (1f-t)),
                (color.g * t) + (l * (1f-t)),
                (color.b * t) + (l * (1f-t))
            )
        } else {
            FloatColor.WHITE
        }
    }
    return color
}
