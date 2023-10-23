Simple repo to demonstrate cross-module protobuf-src dependency resolution issue.

The scenario only seems to exists where there is a protobuf-src scoped `dependsOn` relation to another module, such as the relation between anotherMod -> base modules in build.sbt.

To demonstrate the broken resolution:

```
$> sbt clean test
```

Observe the failure to unpack protobuf-src dependency:

```
[error] (anotherMod / protocUnpackDependencies) java.io.IOException: Dependency /Users/dkichler/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/thesamet/scalapb/scalapb-validate-core_2.13/0.3.2/scalapb-validate-core_2.13-0.3.2.jar manifest references a non-existant proto /Users/dkichler/projects/innovapost/sandbox/protoc-gen-test/another-module/target/protobuf_external_src/scalapb/validate-options.proto
```

The resolution seems to be fixed when the 

```
"com.thesamet.scalapb" %% "scalapb-validate-core" % scalapb.validate.compiler.BuildInfo.version % "protobuf",
```

on line `build.sbt:32`  is commented out, but of course then compilation will also fail. 
