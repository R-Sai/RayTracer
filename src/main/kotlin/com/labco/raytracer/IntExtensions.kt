package com.labco.raytracer

operator fun Int.times(unitVector: UnitVector3): Vector3 = Vector3(this * unitVector.x, this * unitVector.y, this * unitVector.z)