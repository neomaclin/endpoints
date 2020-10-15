package com.quasigroup.inc

import io.circe._
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

  def pagination(pageNumber:String, pageSize:String): EndpointInput[(Int, Int)] =
    query[Int](name = pageNumber) and query[Int]( name = pageSize)

  object Heartbeat {
    def ping(path: String): Endpoint[Unit, Unit, Unit, Nothing] =
      endpoint.in(path).out(statusCode(StatusCode.Ok))
  }

  object Entrance {

    def indexJson[T:Encoder: Decoder:Schema:Validator]: Endpoint[Unit, Unit, T, Nothing] =
      endpoint.get
        .in("/index")
        .out(jsonBody[T]).out(statusCode(StatusCode.Ok))

    val indexHtml: Endpoint[Unit, Unit, String, Nothing] =
      endpoint.get
        .in("/index.html")
        .out(htmlBodyUtf8).out(statusCode(StatusCode.Ok))

  }

  object Authentication{

    def signIn[T:Encoder: Decoder:Schema:Validator, R:Encoder: Decoder:Schema:Validator](signInPath:String): Endpoint[(Int, Option[Int], Option[T]), Unit, R, Nothing] =
      endpoint.post
        .in(signInPath)
        .in(origins)
        .in(jsonBody[Option[T]])
        .out(jsonBody[R]).out(statusCode(StatusCode.Ok))

    def signOut[T](signOutPath:String): Endpoint[Unit, Unit, Unit, Nothing] = endpoint
      .delete
      .in(signOutPath)
      .out(statusCode(StatusCode.Ok))
  }

}
