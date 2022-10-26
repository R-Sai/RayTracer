package com.labco.raytracer.material

import kotlin.math.pow

data class FloatColor(var r: Float, var g: Float, var b: Float) {

    companion object {
        val BLACK = FloatColor(0f, 0f, 0f)
        val WHITE = FloatColor(1f, 1f, 1f)
    }

    fun toGamma() = this.pow(2.2f)

    fun pow(float: Float) = FloatColor(r.pow(float), g.pow(float), b.pow(float))

    operator fun times(color: FloatColor): FloatColor {
        val r = (this.r * color.r)
        val g = (this.g * color.g)
        val b = (this.b * color.b)
        return FloatColor(r, g, b)
    }

    operator fun times(float: Float): FloatColor {
        return FloatColor((r * float), (g * float), (b * float))
    }

    operator fun plus(color: FloatColor): FloatColor {
        val r = (this.r + color.r)
        val g = (this.g + color.g)
        val b = (this.b + color.b)
        return FloatColor(r, g, b)
    }

    operator fun minus(color: FloatColor): FloatColor {
        val r = (this.r - color.r)
        val g = (this.g - color.g)
        val b = (this.b - color.b)
        return FloatColor(r, g, b)
    }

    operator fun div(integer: Int) = FloatColor(this.r / integer.toFloat(), this.g / integer.toFloat(), this.b / integer.toFloat())
    fun coerce() {
        this.r = this.r.coerceIn(0f, 1f)
        this.g = this.g.coerceIn(0f, 1f)
        this.b = this.b.coerceIn(0f, 1f)
    }
}
