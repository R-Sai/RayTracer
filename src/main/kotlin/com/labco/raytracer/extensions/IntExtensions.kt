package com.labco.raytracer

import com.labco.raytracer.common.UnitVector3
import com.labco.raytracer.common.Vector3

operator fun Int.times(unitVector: UnitVector3): Vector3 = Vector3(this * unitVector.x, this * unitVector.y, this * unitVector.z)