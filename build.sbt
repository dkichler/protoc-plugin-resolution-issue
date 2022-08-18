import protocbridge.Target
import sbt.Compile
import sbtprotoc.ProtocPlugin.autoImport.PB
import scalapb.GeneratorOption.FlatPackage

lazy val base = (project in file("base")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.13.6"
    )),
    name := "scalatest-example",
    Compile / PB.targets := Seq(
      scalapb.gen(FlatPackage) -> (Compile / sourceManaged).value / "scalapb",
      PB.gens.java -> (Compile / sourceManaged).value / "java"
    ),
    Compile / PB.targets ++= Seq(
      Target(
        scalapb.validate.gen(FlatPackage),
        (Compile / sourceManaged).value / "scalapb"
      ),
      Target(
        PB.gens.plugin("validate"),
        (Compile / sourceManaged).value / "java",
        Seq("lang=java")
      )
    ),
    libraryDependencies ++= Seq(
      "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",
      "com.thesamet.scalapb" %% "scalapb-validate-core" % scalapb.validate.compiler.BuildInfo.version,
      "com.thesamet.scalapb" %% "scalapb-validate-core" % scalapb.validate.compiler.BuildInfo.version % "protobuf",
      "io.envoyproxy.protoc-gen-validate" % "pgv-java-stub" % "0.6.7" % "protobuf,compile",
      ("io.envoyproxy.protoc-gen-validate" % "protoc-gen-validate" % "0.6.7").asProtocPlugin,
      "org.scalatest" %% "scalatest" % "3.2.12" % Test
    )
  )

lazy val anotherMod = (project in file("another-module"))
  .dependsOn(base % "test->test;compile->compile;protobuf->protobuf")

lazy val root = (project in file(".")).aggregate(base, anotherMod)
