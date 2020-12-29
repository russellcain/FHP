package com.cain.util.marshalling
import akka.http.scaladsl.marshalling.{Marshaller, ToEntityMarshaller}
import akka.http.scaladsl.model.MediaTypes
import akka.http.scaladsl.unmarshalling.{FromByteStringUnmarshaller, FromEntityUnmarshaller, Unmarshaller}
import akka.http.scaladsl.util.FastFuture
import org.json4s.jackson.{Serialization, parseJson}

trait JSON {

  implicit val formats:org.json4s.DefaultFormats = org.json4s.DefaultFormats

  implicit def genericByteStringUnmarshaller[T](implicit m: Manifest[T]): FromByteStringUnmarshaller[T] =
    Unmarshaller.withMaterializer(_ => implicit mat => { bs =>
      FastFuture.successful(parseJson(bs.utf8String).extract[T])
    })

  implicit def genericUnmarshaller[T](implicit m: Manifest[T]): FromEntityUnmarshaller[T] =
    Unmarshaller.byteStringUnmarshaller.forContentTypes(MediaTypes.`application/json`).andThen(genericByteStringUnmarshaller)

  implicit def genericMarshaller[T <: AnyRef]: ToEntityMarshaller[T] =
    Marshaller.StringMarshaller.wrap(MediaTypes.`application/json`)(Serialization.write[T])

}