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
    name := "protoc-gen-test",
    libraryDependencies ++= Seq(
      "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",
      "com.thesamet.scalapb" %% "scalapb-validate-core" % scalapb.validate.compiler.BuildInfo.version,
      "com.thesamet.scalapb" %% "scalapb-validate-core" % scalapb.validate.compiler.BuildInfo.version % "protobuf",
      "build.buf.protoc-gen-validate" % "pgv-java-stub" % "1.0.0" % "protobuf,compile",
      ("build.buf.protoc-gen-validate" % "protoc-gen-validate" % "1.0.0").asProtocPlugin,
      "org.scalatest" %% "scalatest" % "3.2.15" % Test
    )
  )
  .settings(commonSettings)

lazy val anotherMod = (project in file("another-module"))
  .dependsOn(base % "test->test;compile->compile")
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "com.thesamet.scalapb" %% "scalapb-validate-core" % scalapb.validate.compiler.BuildInfo.version,
      "com.thesamet.scalapb" %% "scalapb-validate-core" % scalapb.validate.compiler.BuildInfo.version % "protobuf",
      "build.buf.protoc-gen-validate" % "pgv-java-stub" % "1.0.0" % "protobuf,compile",
      ("build.buf.protoc-gen-validate" % "protoc-gen-validate" % "1.0.0").asProtocPlugin
    )
  )

lazy val root = (project in file(".")).aggregate(base, anotherMod)

val commonSettings = Seq(
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
  )
)
