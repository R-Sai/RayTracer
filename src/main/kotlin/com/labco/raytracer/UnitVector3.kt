package com.labco.raytracer

import kotlin.math.pow
import kotlin.math.sqrt

class UnitVector3(x: Float, y: Float, z: Float) {

    init {
        set(x, y, z)
    }

    var x = 0f
        private set

    var y = 0f
        private set

    var z = 0f
        private set

    fun set(x: Float, y: Float, z: Float) {
        val magnitude = sqrt(x.pow(2) + y.pow(2) + z.pow(2))
        this.x = x / magnitude
        this.y = y / magnitude
        this.z = z / magnitude
    }

    infix fun dot(unitVector: UnitVector3) = (this.x * unitVector.x) + (this.y * unitVector.y) + (this.z * unitVector.z)

    infix fun dot(vector3: Vector3) = (this.x * vector3.x) + (this.y * vector3.y) + (this.z * vector3.z)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UnitVector3

        if (x != other.x) return false
        if (y != other.y) return false
        if (z != other.z) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }

    operator fun plus(unitVector: UnitVector3) = Vector3(this.x + unitVector.x, this.y + unitVector.y, this.z + unitVector.z)

    operator fun plus(vector3: Vector3) = Vector3(this.x + vector3.x, this.y + vector3.y, this.z + vector3.z)

    operator fun times(int: Int) = Vector3(this.x * int, this.y * int, this.z * int)

    operator fun times(Float: Float) = Vector3(this.x * Float, this.y * Float, this.z * Float)

    operator fun times(unitVector: UnitVector3) = UnitVector3(
        this.y * unitVector.z - this.z * unitVector.y,
        this.z * unitVector.x - this.x * unitVector.z,
        this.x * unitVector.y - this.y * unitVector.x,
    )

    operator fun unaryMinus(): UnitVector3 = UnitVector3(-this.x, -this.y, -this.z)

    operator fun minus(vector3: Vector3) = Vector3(this.x - vector3.x, this.y - vector3.y, this.z - vector3.z)
}