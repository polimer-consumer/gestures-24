package com.polimerconsumer.gestures24.geom

data class Border(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {

    fun intersects(other: Border): Boolean {
        val d = (x1 - x2) * (other.y1 - other.y2) - (y1 - y2) * (other.x1 - other.x2)
        if (d == 0) return false

        val t = ((x1 - other.x1) * (other.y1 - other.y2) - (y1 - other.y1) * (other.x1 - other.x2)) / d.toDouble()
        val u = ((x1 - x2) * (y1 - other.y1) - (y1 - y2) * (x1 - other.x1)) / d.toDouble()

        return t in 0.0..1.0 && u in 0.0..1.0
    }
}
