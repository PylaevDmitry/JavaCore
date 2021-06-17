package profSoftTest

fun main() {
    val sphere = Sphere(2.0)
    println(sphere)
    println("Основание ${String.format("%.3f", sphere.getSquare())}, объем  ${String.format("%.3f", sphere.getVolume())}")
    println()

    val cube = Cube(1.3)
    cube.color= "Black"
    println(cube)
    println("Основание ${String.format("%.3f", cube.getSquare())}, объем  ${String.format("%.3f", cube.getVolume())}")
    println()

    val cone = Cone (0.4, 1.7)
    cone.r = 1000.0
    println(cone)
    println("Основание ${String.format("%.3f", cone.getSquare())}, объем  ${String.format("%.3f", cone.getVolume())}")
    println()
}

abstract class Figure {
    open var color: String = String()
    abstract fun getVolume():Double
    abstract fun getSquare():Double
}

class Sphere(var r:Double): Figure() {
    init { super.color = "Red" }
    override fun getVolume() = 4*Math.PI*r*r*r/3
    override fun getSquare() = 0.0
    override fun toString() = "Sphere(r=$r), color is $color"
}

class Cube(var a:Double): Figure() {
    init { super.color = "Blue" }
    override fun getVolume() = a*a*a
    override fun getSquare() = a*a
    override fun toString() = "Cube(a=$a), color is $color"
}

class Cone(var r:Double, var h:Double): Figure() {
    init { super.color = "Green" }
    override fun getVolume() = Math.PI*r*r*h/3
    override fun getSquare() = Math.PI*r*r
    override fun toString() = "Cone(r=$r, h=$h), color is $color"
}