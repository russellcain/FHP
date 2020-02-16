package com.cain.fhp.util.marshalling

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}
import com.cain.fhp.persistence.Tables._

trait AppStarterMarshaller extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val playerRow: RootJsonFormat[PlayersRow] = jsonFormat10(PlayersRow)
}