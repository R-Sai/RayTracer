package com.labco.raytracer.material

data class RGBColor(var r: UByte, var g: UByte, var b: UByte) {

    companion object {
        val BLACK = RGBColor(0u, 0u, 0u)
        val WHITE = RGBColor(255u, 255u, 255u)
    }

    // todo fix this ugly mess
    operator fun times(color: RGBColor): RGBColor {
        val r = ((this.r.toFloat()/255) * (color.r.toFloat()/255) * 255).coerceAtMost(UByte.MAX_VALUE.toFloat()).toUInt().toUByte()
        val g = ((this.g.toFloat()/255) * (color.g.toFloat()/255) * 255).coerceAtMost(UByte.MAX_VALUE.toFloat()).toUInt().toUByte()
        val b = ((this.b.toFloat()/255) * (color.b.toFloat()/255) * 255).coerceAtMost(UByte.MAX_VALUE.toFloat()).toUInt().toUByte()
        return RGBColor(r, g, b)
    }

    operator fun times(float: Float): RGBColor {
        val unsignedInt = (float * 1000).toUInt()
        val r = ((this.r * unsignedInt) / 1000u).coerceAtMost(UByte.MAX_VALUE.toUInt()).toUByte()
        val g = ((this.g * unsignedInt) / 1000u).coerceAtMost(UByte.MAX_VALUE.toUInt()).toUByte()
        val b = ((this.b * unsignedInt) / 1000u).coerceAtMost(UByte.MAX_VALUE.toUInt()).toUByte()
        return RGBColor(r, g, b)
    }

    operator fun plus(color: RGBColor): RGBColor {
        val r = (this.r + color.r).coerceAtMost(UByte.MAX_VALUE.toUInt()).toUByte()
        val g = (this.g + color.g).coerceAtMost(UByte.MAX_VALUE.toUInt()).toUByte()
        val b = (this.b + color.b).coerceAtMost(UByte.MAX_VALUE.toUInt()).toUByte()
        return RGBColor(r, g, b)
    }

    operator fun plus(uInt: UInt): RGBColor {
        val r = (this.r + uInt).coerceAtMost(UByte.MAX_VALUE.toUInt()).toUByte()
        val g = (this.g + uInt).coerceAtMost(UByte.MAX_VALUE.toUInt()).toUByte()
        val b = (this.b + uInt).coerceAtMost(UByte.MAX_VALUE.toUInt()).toUByte()
        return RGBColor(r, g, b)
    }

    operator fun plus(uByte: UByte): RGBColor {
        val r = (this.r + uByte).coerceAtMost(UByte.MAX_VALUE.toUInt()).toUByte()
        val g = (this.g + uByte).coerceAtMost(UByte.MAX_VALUE.toUInt()).toUByte()
        val b = (this.b + uByte).coerceAtMost(UByte.MAX_VALUE.toUInt()).toUByte()
        return RGBColor(r, g, b)
    }

    operator fun minus(color: RGBColor): RGBColor {
        val r = (this.r - color.r).coerceIn(0u, UByte.MAX_VALUE.toUInt()).toUByte()
        val g = (this.g - color.g).coerceIn(0u, UByte.MAX_VALUE.toUInt()).toUByte()
        val b = (this.b - color.b).coerceIn(0u, UByte.MAX_VALUE.toUInt()).toUByte()
        return RGBColor(r, g, b)
    }
}
