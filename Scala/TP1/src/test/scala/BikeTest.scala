import fr.uge.td.scala.Bike
import fr.uge.td.scala.BikeV2
import fr.uge.td.scala.BikeV3

import org.scalatest.funsuite.AnyFunSuite

class BikeTest extends AnyFunSuite {
  test("Bike should take or not speed in base constructor") {
    val bikeWithoutSpeedArgument = new Bike("White", 3)
    val bikeWithSpeedArgument = new Bike("Yellow", 3, 10)
    assert(bikeWithoutSpeedArgument.speed == 0)
    assert(bikeWithSpeedArgument.speed == 10)
  }

  test("SpeedUp and Brake increase/decrease bike's speed") {
    val bike = new Bike("White", 3, 10)
    assert(bike.speed == 10)
    bike.speedUp(5)
    assert(bike.speed == 15)
    bike.brake(10)
    assert(bike.speed == 5)
  }

  test("Should throw an IllegalArgumentException if given speed is negative") {
    val bike = new Bike("White", 3, 10)
    assertThrows[IllegalArgumentException](bike.speedUp(-5))
  }

  test("bikeV3") {
    val bike = BikeV3("White", 3, 10)
    val bikeNewSpeed = bike.speedUp(5)
    assert(bikeNewSpeed.speed == 15)
  }
}