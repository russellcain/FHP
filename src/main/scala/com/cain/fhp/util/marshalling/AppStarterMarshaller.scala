package com.cain.fhp.util.marshalling

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}
import com.cain.fhp.persistence.Tables._
import com.cain.fhp.util.general.Requests.QueryName

trait AppStarterMarshaller extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val playerRow: RootJsonFormat[PlayersRow] = jsonFormat10(PlayersRow)
  implicit val queryName: RootJsonFormat[QueryName] = jsonFormat1(QueryName)
}