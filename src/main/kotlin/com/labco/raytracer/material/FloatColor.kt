package com.labco.raytracer.material

import kotlin.math.pow

data class FloatColor(var r: Float, var g: Float, var b: Float) {

    companion object {
        val BLACK = FloatColor(0f, 0f, 0f)
        val WHITE = FloatColor(1f, 1f, 1f)
    }

    fun toGamma() = this.pow(2.2f)

    fun pow(float: Float) = FloatColor(r.pow(float), g.pow(float), b.pow(float))

    // todo fix this ugly mess
    operator fun times(color: FloatColor): FloatColor {
        val r = (this.r * color.r).coerceIn(0f, 1f)
        val g = (this.g * color.g).coerceIn(0f, 1f)
        val b = (this.b * color.b).coerceIn(0f, 1f)
        return FloatColor(r, g, b)
    }

    operator fun times(float: Float): FloatColor {
        return FloatColor((r * float).coerceIn(0f, 1f), (g * float).coerceIn(0f, 1f), (b * float).coerceIn(0f, 1f))
    }

    operator fun plus(color: FloatColor): FloatColor {
        val r = (this.r + color.r).coerceIn(0f, 1f)
        val g = (this.g + color.g).coerceIn(0f, 1f)
        val b = (this.b + color.b).coerceIn(0f, 1f)
        return FloatColor(r, g, b)
    }

    operator fun minus(color: FloatColor): FloatColor {
        val r = (this.r - color.r).coerceIn(0f, 1f)
        val g = (this.g - color.g).coerceIn(0f, 1f)
        val b = (this.b - color.b).coerceIn(0f, 1f)
        return FloatColor(r, g, b)
    }

    operator fun div(integer: Int) = FloatColor(this.r / integer.toFloat(), this.g / integer.toFloat(), this.b / integer.toFloat())
}
