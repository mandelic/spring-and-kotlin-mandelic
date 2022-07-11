fun main() {
        if (CarCheckUpSystem.isCheckUpNecessary("1C4PJMDB6FW698828") == true) println("The car check-up is necessary.")
        else println("The car check-up is not necessary.")
        val newCheckUp = CarCheckUpSystem.addCheckUp("5GAKRCKD6DJ199296")
        if (newCheckUp != null)
            println("New check-up added: (Performed at: ${newCheckUp.performedAt}, vin: ${newCheckUp.car.vin})")
        val vinCheckUps = CarCheckUpSystem.getCheckUps("5GAKRCKD6DJ199296")
        vinCheckUps?.forEach { checkUp -> println("Performed at: ${checkUp.performedAt}, vin: ${checkUp.car.vin}")}
        println(CarCheckUpSystem.countCheckUps("Audi"))
}

