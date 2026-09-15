package fr.uge.td.scala


class Bike(val color : String, val wheelSize : Int, var speed : Int = 0) {

  def speedUp(value:Int) : Unit = {
    require(value >= 0, "Value must be positive.")
    speed += value
  }

  def brake(value:Int) : Unit = {
    require(value >= 0, "Value must be positive.")
    if (value > speed) speed = 0
    else speed -= value
  }
}


class BikeV2(val color : String, val wheelSize : Int, val speed : Int = 0) {

  def speedUp(value:Int) : BikeV2 = {
    require(value >= 0, "Value must be positive.")
    new BikeV2(color, wheelSize, speed + value)
  }

  def brake(value:Int) : BikeV2 = {
    require(value >= 0, "Value must be positive.")
    if (value > speed) new BikeV2(color, wheelSize, 0)
    else new BikeV2(color, wheelSize, speed - value)
  }
}

case class BikeV3(color : String, wheelSize : Int, speed : Int = 0) {
  def speedUp(value: Int): BikeV3 = {
    require(value >= 0, "Value must be positive.")
    copy(speed = speed + value)
  }

  def brake(value: Int): BikeV3 = {
    require(value >= 0, "Value must be positive.")
    if (value > speed) copy(speed = 0)
    copy(speed = speed - value)
  }
}

case class BikeV4(color : String, wheelSize : Int, speed : Int = 0) {
  def speedUp(value: Int): Option[BikeV4] = {
    copy(speed = speed + value)
  }

  def brake(value: Int): Option[BikeV4] = {
  }
}