import protocbridge.Target
import sbt.Compile
import sbt._
import sbt.Keys._
import sbtprotoc.ProtocPlugin.autoImport.PB
import scalapb.GeneratorOption.FlatPackage


lazy val root = (project in file("."))
  .enablePlugins(BuildInfoPlugin)
  .settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.13.6"
    )),
    name := "protoc-gen-example",
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
        (Compile / sourceManaged).value,
        Seq("lang=java")
      )
    ),
    libraryDependencies ++= Seq(
      "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",
      "com.thesamet.scalapb" %% "scalapb-validate-core" % scalapb.validate.compiler.BuildInfo.version,
      "com.thesamet.scalapb" %% "scalapb-validate-core" % scalapb.validate.compiler.BuildInfo.version % "protobuf",
      "io.envoyproxy.protoc-gen-validate" % "pgv-java-stub" % "0.6.2" % "protobuf,compile",
      ("io.envoyproxy.protoc-gen-validate" % "protoc-gen-validate" % "0.6.2").asProtocPlugin,
      "org.scalatest" %% "scalatest" % "3.2.9" % Test
    )
  )

lazy val anotherMod = (project in file("another-module"))
  .dependsOn(root % "compile->compile;protobuf->protobuf")



