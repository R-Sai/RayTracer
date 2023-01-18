package com.labco.raytracer

import com.labco.raytracer.common.UnitVector3
import com.labco.raytracer.common.Vector3
import com.labco.raytracer.material.FloatColor
import com.labco.raytracer.material.RGBColor


fun Float.meterToMillimeter() = this * 1000

fun Float.millimeterToMeter() = this / 1000

operator fun Float.times(unitVector: UnitVector3): Vector3 = Vector3(unitVector.x * this, unitVector.y * this, unitVector.z * this)

operator fun Float.times(color: RGBColor): RGBColor {
    val unsignedInt = (this * 1000).toUInt()
    val r = ((color.r * unsignedInt) / 1000u).coerceAtMost(UByte.MAX_VALUE.toUInt()).toUByte()
    val g = ((color.g * unsignedInt) / 1000u).coerceAtMost(UByte.MAX_VALUE.toUInt()).toUByte()
    val b = ((color.b * unsignedInt) / 1000u).coerceAtMost(UByte.MAX_VALUE.toUInt()).toUByte()
    return RGBColor(r, g, b)
}

operator fun Float.times(floatColor: FloatColor) = FloatColor(this * floatColor.r, this * floatColor.g, this * floatColor.b)