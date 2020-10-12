name := "endpoints"

version := "0.1"

scalaVersion := "2.13.2"
libraryDependencies ++= webStack ++ crypto ++ stackGlue

lazy val webStack = circe ++ sttp ++ jwt
lazy val stackGlue = enumlib ++ macwire
lazy val crypto = bc

lazy val bc = {
  // https://mvnrepository.com/artifact/org.bouncycastle/bcprov-jdk15to18
  Seq("org.bouncycastle" % "bcprov-jdk15to18" % "1.66")

}

lazy val macwire = {
  val macWireVersion = "2.3.6"
  Seq(
    "com.softwaremill.macwire" %% "macros" % macWireVersion % Provided,
    "com.softwaremill.macwire" %% "macrosakka" % macWireVersion % Provided,
    "com.softwaremill.macwire" %% "util" % macWireVersion,
    "com.softwaremill.macwire" %% "proxy" % macWireVersion
  )
}

lazy val enumlib = {
  Seq(
    "com.beachape" %% "enumeratum",
    "com.beachape" %% "enumeratum-circe",
    "com.beachape" %% "enumeratum-quill"
  ).map(_ %  "1.6.0")
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
  ).map( _ % "0.16.15" ) ++
    Seq(
      "com.softwaremill.sttp.client" %% "core" % "2.2.5",
      "com.softwaremill.sttp.model" %% "core" % "1.1.4"
    )
}

lazy val jwt = {
  Seq(
    "com.pauldijou" %% "jwt-circe" % "4.2.0"
  )
}