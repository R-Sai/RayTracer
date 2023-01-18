package com.labco.raytracer.common

import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

data class Vector3(var x: Float, var y: Float, var z: Float) {

    infix fun dot(unitVector: UnitVector3): Float {
        return (this.x * unitVector.x) + (this.y * unitVector.y) + (this.z * unitVector.z)
    }

    fun getLength() = sqrt(x.pow(2) + y.pow(2) + z.pow(2))

    fun toUnitVector3() = UnitVector3(this.x, this.y, this.z)

    fun jiggle(maxJiggle: Float): Vector3 {
        return Vector3(
        this.x + Random.nextDouble(-1.0, 1.0).toFloat() * maxJiggle,
        this.y + Random.nextDouble(-1.0, 1.0).toFloat() * maxJiggle,
        this.z + Random.nextDouble(-1.0, 1.0).toFloat() * maxJiggle
        )
    }

    infix fun dot(position: Vector3): Float {
        return (this.x * position.x) + (this.y * position.y) + (this.z * position.z)
    }

    operator fun minus(position: Vector3) = Vector3(this.x - position.x, this.y - position.y, this.z - position.z)

    operator fun times(float: Float): Vector3 = Vector3(this.x - float, this.y - float, this.z - float)

    operator fun plus(position: Vector3) = Vector3(this.x + position.x, this.y + position.y, this.z + position.z)

    operator fun plus(unitVector: UnitVector3) =
        Vector3(this.x + unitVector.x, this.y + unitVector.y, this.z + unitVector.z)

    operator fun unaryMinus() = Vector3(-this.x, -this.y, -this.z)

    operator fun div(float: Float) = Vector3(this.x / float, this.y / float, this.z / float)
}