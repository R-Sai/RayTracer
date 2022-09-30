package com.labco.raytracer.material

class Material(
    var color: FloatColor,
    var shininess: Float,
    var specularCoefficient: Float,
    var reflectiveness: Float
) {

    companion object {
        fun metalic(color: FloatColor) = Material(color, 1000f, 10f, 0.9f)
    }
}