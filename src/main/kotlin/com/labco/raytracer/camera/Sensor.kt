package com.labco.raytracer.camera

import com.labco.raytracer.material.FloatColor

data class Sensor(val width: Float, val height: Float, val resolutionX: Int, val resolutionY: Int) {

    val pixels: Array<Array<FloatColor>> = Array(resolutionX) { Array(resolutionY) { FloatColor.BLACK } }

}