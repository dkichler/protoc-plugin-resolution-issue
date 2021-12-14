addSbtPlugin("com.thesamet" % "sbt-protoc" % "1.0.4")
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.10.0")

libraryDependencies ++= Seq(
  "com.thesamet.scalapb" %% "compilerplugin" % "0.11.6",
  //"com.thesamet.scalapb" %% "scalapb-validate-codegen" % "0.3.2"
)
