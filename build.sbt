organization := "com.quasigroup.inc"

name := "endpoints"

version := "0.1"

scalaVersion := "2.13.2"
libraryDependencies ++= webStack ++ crypto ++ stackGlue

lazy val webStack = circe ++ sttp ++ jwt
lazy val stackGlue = enumlib
lazy val crypto = bc

lazy val bc = {
  // https://mvnrepository.com/artifact/org.bouncycastle/bcprov-jdk15to18
  Seq("org.bouncycastle" % "bcprov-jdk15to18" % "1.66")

}

lazy val enumlib = {
  Seq(
    "com.beachape" %% "enumeratum",
    "com.beachape" %% "enumeratum-circe",
    "com.beachape" %% "enumeratum-quill"
  ).map(_ % "1.6.0")
}

lazy val circe = {
  val circeVersion = "0.13.0"
  Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser"
  ).map(_ % circeVersion)
}


lazy val sttp = {
  Seq(
    "com.softwaremill.sttp.tapir" %% "tapir-json-circe",
    "com.softwaremill.sttp.tapir" %% "tapir-core",
    "com.softwaremill.sttp.tapir" %% "tapir-zio",
    "com.softwaremill.sttp.tapir" %% "tapir-akka-http-server",
    "com.softwaremill.sttp.tapir" %% "tapir-sttp-client",
    "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs",
    "com.softwaremill.sttp.tapir" %% "tapir-openapi-circe-yaml"
  ).map(_ % "0.16.15") ++
    Seq(
      "com.softwaremill.sttp.model" %% "core" % "1.1.4",
      "com.softwaremill.sttp.client" %% "core" % "2.2.5",
    ) ++
    Seq(
      "com.softwaremill.sttp.client" %% "async-http-client-backend-zio",
      "com.softwaremill.sttp.client" %% "async-http-client-backend-future",
      "com.softwaremill.sttp.client" %% "akka-http-backend",
      "com.softwaremill.sttp.client" %% "okhttp-backend"
    ).map(_ % "2.2.9")
}

lazy val jwt = {
  Seq(
    "com.pauldijou" %% "jwt-circe" % "4.2.0"
  )
}