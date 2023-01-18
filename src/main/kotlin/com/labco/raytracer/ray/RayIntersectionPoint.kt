package com.labco.raytracer.ray

import com.labco.raytracer.common.UnitVector3
import com.labco.raytracer.common.Vector3

data class RayIntersectionPoint(
    var position: Vector3,
    var normal: UnitVector3
)
