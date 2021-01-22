organization := "com.quasigroup.inc"

name := "endpoints"

version := "0.9"

scalaVersion := "2.13.4"

libraryDependencies ++= webStack ++ crypto ++ stackGlue

lazy val webStack =  sttp ++ jwt
lazy val stackGlue = enumlib ++ cats
lazy val crypto = bc

lazy val cats = {
  Seq(
    "org.typelevel" %% "kittens" % "2.2.1"
  ) ++
    Seq(
      "org.typelevel" %% "cats-effect",
      "org.typelevel" %% "cats-core",
      "org.typelevel" %% "cats-free"
    ).map(_ % "2.3.1")
}


lazy val bc = {
  Seq("org.bouncycastle" % "bcprov-jdk15on" % "1.68")
}

lazy val enumlib = {
  Seq(
    "com.beachape" %% "enumeratum",
    "com.beachape" %% "enumeratum-circe",
    "com.beachape" %% "enumeratum-cats",
  ).map(_ % "1.6.1")
}

lazy val sttp = {
  Seq(
    "com.softwaremill.sttp.tapir" %% "tapir-json-circe",
    "com.softwaremill.sttp.tapir" %% "tapir-core",
    "com.softwaremill.sttp.tapir" %% "tapir-zio",
    "com.softwaremill.sttp.tapir" %% "tapir-http4s-server",
    "com.softwaremill.sttp.tapir" %% "tapir-akka-http-server",
    "com.softwaremill.sttp.tapir" %% "tapir-sttp-client",
    "com.softwaremill.sttp.tapir" %% "tapir-enumeratum",
    "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs",
    "com.softwaremill.sttp.tapir" %% "tapir-openapi-circe-yaml",
    "com.softwaremill.sttp.tapir" %% "tapir-asyncapi-docs" ,
    "com.softwaremill.sttp.tapir" %% "tapir-asyncapi-circe-yaml" ,
  ).map(_ % "0.17.7") ++
    Seq(
      "com.softwaremill.sttp.model" %% "core" % "1.2.0",
      "com.softwaremill.sttp.shared" %% "core" % "1.0.0",
      "com.softwaremill.sttp.shared" %% "akka" % "1.0.0"
    ) ++
    Seq(
      "com.softwaremill.sttp.client3" %% "akka-http-backend",
      "com.softwaremill.sttp.client3" %% "okhttp-backend",
      "com.softwaremill.sttp.client3" %% "core"
    ).map(_ % "3.0.0")
}

lazy val jwt = {
  Seq(
    "com.pauldijou" %% "jwt-circe" % "5.0.0"
  )
}