package com.cain.fhp.util

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.alpakka.slick.scaladsl.SlickSession

import scala.concurrent.ExecutionContext

/**
  * Sets up actor system/materializer/dispatcher
  *
  * Each inheritance of this trait will spin up a new actor system for use.
  */

trait AkkaService {

  // actor system initialization
  implicit lazy val system: ActorSystem = ActorSystem()
  implicit lazy val materializer: ActorMaterializer = ActorMaterializer()
  implicit lazy val ec: ExecutionContext = system.dispatcher

  // db session (I hope)
  implicit lazy val session: SlickSession = SlickSession.forConfig("slick-postgres")

  lazy val PARALLELISM_FACTOR = 4

}
