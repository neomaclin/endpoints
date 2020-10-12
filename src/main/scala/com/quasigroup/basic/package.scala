package com.quasigroup

import io.circe._
import sttp.tapir.Codec._
import sttp.tapir._
import sttp.tapir.json.circe._
import io.circe.generic.auto._
import sttp.model.StatusCode


package object basic {
  sealed trait ErrorInfo

  case class NotFound(what: String) extends ErrorInfo
  case class Unauthorized(realm: String) extends ErrorInfo
  case class Unknown(code: Int, msg: String) extends ErrorInfo
  case object NoContent extends ErrorInfo

  // here we are defining an error output, but the same can be done for regular outputs
  val baseEndpoint: Endpoint[Unit, ErrorInfo, Unit, Nothing] = endpoint.errorOut(
    oneOf[ErrorInfo](
      statusMapping(StatusCode.NotFound, jsonBody[NotFound].description("not found")),
      statusMapping(StatusCode.Unauthorized, jsonBody[Unauthorized].description("unauthorized")),
      statusMapping(StatusCode.NoContent, emptyOutput.map(_ => NoContent)(_ => ())),
      statusDefaultMapping(jsonBody[Unknown].description("unknown"))
    )
  )
  val origins:EndpointInput[(Int, Option[Int])] =
    query[Int]("from") and query[Option[Int]]("with")

  val pagination: EndpointInput[(Int, Int)] =
    query[Int]("pageNo") and query[Int]("pageSize")

  object Heartbeat {
    val ping: Endpoint[Unit, Unit, String, Nothing] =
      endpoint.get
        .in("/ping")
        .out(stringBody).out(statusCode(StatusCode.Ok))
  }

  object Entrance {

    def indexJson[T: JsonCodec: Encoder: Decoder]: Endpoint[Unit, Unit, T, Nothing] =
      endpoint.get
        .in("/index")
        .out(jsonBody[T]).out(statusCode(StatusCode.Ok))

    def indexHtml[T: XmlCodec]: Endpoint[Unit, Unit, T, Nothing] =
      endpoint.get
        .in("/index.html")
        .out(xmlBody[T]).out(statusCode(StatusCode.Ok))

  }

  object Authentication{

    def signIn[T: JsonCodec: Encoder: Decoder, R]: Endpoint[(Int, Option[Int], Option[T], Option[T]), Unit, R, Nothing] =
      endpoint.post
        .in("/sign_in")
        .in(origins)
        .in(jsonBody[Option[T]] and formBody[Option[T]])
        .out(jsonBody[R]).out(statusCode(StatusCode.Ok))

    def signOut[T]: Endpoint[Unit, Unit, Unit, Nothing] = endpoint
      .delete
      .in("sign_out")
      .out(statusCode(StatusCode.Ok))
  }

}
