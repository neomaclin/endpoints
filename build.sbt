organization := "com.quasigroup.inc"

name := "endpoints"

version := "0.8"

scalaVersion := "2.13.4"

libraryDependencies ++= webStack ++ crypto ++ stackGlue

lazy val webStack = circe ++ sttp ++ jwt
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
    ).map(_ % "2.3.0")
}


lazy val bc = {
  // https://mvnrepository.com/artifact/org.bouncycastle/bcprov-jdk15to18
  Seq("org.bouncycastle" % "bcprov-jdk15to18" % "1.66")
}

lazy val enumlib = {
  Seq(
    "com.beachape" %% "enumeratum",
    "com.beachape" %% "enumeratum-circe",
    "com.beachape" %% "enumeratum-cats",
  ).map(_ % "1.6.1")
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
    "com.softwaremill.sttp.tapir" %% "tapir-enumeratum",
    "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs",
    "com.softwaremill.sttp.tapir" %% "tapir-openapi-circe-yaml",
    "com.softwaremill.sttp.tapir" %% "tapir-asyncapi-docs" ,
    "com.softwaremill.sttp.tapir" %% "tapir-asyncapi-circe-yaml" ,
  ).map(_ % "0.17.0-M10") ++
    Seq(
      "com.softwaremill.sttp.model" %% "core" % "1.1.4",
      "com.softwaremill.sttp.client" %% "core" % "2.2.5",
      "com.softwaremill.sttp.shared" %% "core" % "1.0.0-RC7",
      "com.softwaremill.sttp.shared" %% "akka" % "1.0.0-RC7",
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