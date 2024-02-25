open class Vehiculo(
    val marca: String,
    val modelo: String,
    val capacidadCombustible: Float,
    var combustibleActual: Float,
    var kilometrosActuales: Float
) {

    companion object {
        const val KM_POR_LITRO = 10
    }

    open fun obtenerInformacion(): String {
        val kilometrosARecorrer = combustibleActual * KM_POR_LITRO
        return "Puede recorrer $kilometrosARecorrer kilómetros"
    }

    open fun calcularAutonomia(): Float {
        return capacidadCombustible * KM_POR_LITRO
    }

    open fun realizarViaje(distancia: Float): Float {
        val capacidadActual = combustibleActual * KM_POR_LITRO
        val distanciaRealizable = if (distancia <= capacidadActual) {
            distancia
        } else {
            capacidadActual / KM_POR_LITRO
        }
        val combustibleGastado = distanciaRealizable / KM_POR_LITRO
        combustibleActual -= combustibleGastado
        kilometrosActuales += distanciaRealizable
        return distanciaRealizable
    }


    open fun repostar(cantidad: Float): Float {
        if (cantidad <= 0 || cantidad + combustibleActual > capacidadCombustible) {
            combustibleActual = capacidadCombustible
        } else {
            combustibleActual += cantidad
        }
        return combustibleActual
    }
}

open class Automovil(
    marca: String,
    modelo: String,
    capacidadCombustible: Float,
    combustibleActual: Float,
    kilometrosActuales: Float,
    val esHibrido: Boolean,
    var condicionBritanica: Boolean
) : Vehiculo(marca, modelo, capacidadCombustible, combustibleActual, kilometrosActuales) {

    companion object {
        const val KM_POR_LITRO = 15

        fun cambiarCondicionBritanica(automovil: Automovil, nuevaCondicion: Boolean) {
            automovil.condicionBritanica = nuevaCondicion
        }

    }

    override fun calcularAutonomia(): Float {
        val autonomiaBase = super.calcularAutonomia()
        var autonomiaFinal = autonomiaBase
        if (esHibrido) {
            autonomiaFinal += KM_POR_LITRO
        }
        return autonomiaFinal
    }

    fun realizaDerrape(): Float {
        var distanciaRecorrida = 7.5f
        if (esHibrido) {
            distanciaRecorrida = 6.25f
        }
        val combustibleGastado = distanciaRecorrida / KM_POR_LITRO
        combustibleActual -= combustibleGastado
        return combustibleActual
    }
}

class Motocicleta(
    marca: String,
    modelo: String,
    capacidadCombustible: Float,
    combustibleActual: Float,
    kilometrosActuales: Float,
    val cilindrada: Int
) : Vehiculo(marca, modelo, capacidadCombustible, combustibleActual, kilometrosActuales) {

    override fun calcularAutonomia(): Float {
        val autonomiaBase = super.calcularAutonomia()
        val autonomiaFinal = if (cilindrada == 1000) autonomiaBase else autonomiaBase - (cilindrada / 1000f)
        return autonomiaFinal
    }

    fun realizaCaballito(): Float {
        val distanciaRecorrida = 6.5f
        val combustibleGastado = distanciaRecorrida / KM_POR_LITRO
        combustibleActual -= combustibleGastado
        return combustibleActual
    }
}

fun main() {
    val automovil = Automovil("Ford", "Fiesta", 40f, 20f, 1000f, true, true)
    val motocicleta = Motocicleta("Yamaha", "YZF-R6", 20f, 15f, 500f, 600)

    println(automovil.obtenerInformacion())
    println("Autonomía: ${automovil.calcularAutonomia()}")
    println("Distancia recorrida: ${automovil.realizarViaje(200f)}")
    println("Repostado: ${automovil.repostar(10f)}")
    println("Combustible restante luego de realizar el derrape: ${automovil.realizaDerrape()}")
    Automovil.cambiarCondicionBritanica(automovil, false)

    println(motocicleta.obtenerInformacion())
    println("Autonomía: ${motocicleta.calcularAutonomia()}")
    println("Distancia recorrida: ${motocicleta.realizarViaje(200f)}")
    println("Repostado: ${motocicleta.repostar(10f)}")
    println("Combustible restante luego de realizar el caballito: ${motocicleta.realizaCaballito()}")

}
