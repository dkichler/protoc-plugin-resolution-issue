# Simple branch to demonstrate odd issue seemingly related to a co-integration with BuildInfoPlugin.

Steps to reproduce:
1. Check out tag `build-info-compilation-broken`
2. Follow commands below to demonstrate compilation failure

Steps showing BuildInfo compilation works when validate generation tasks are disabled:
1. Check out tag `build-info-compiles-without-valildation-enabled`
2. Follow commands below to observe that compilation now works without explicitly invoking `managedSources`


## Commands to reproduce

The issue occurs on the first compilation, whereby for some reason the BuildInfo.scala does not get generated as a managed source when it is expected to be, and
the subsequent compilation fails.  This can be demonstrated with:

```
$> sbt "clean;compile"
```

Without cleaning the project, another immediate compile will succeed as expected:

```
sbt "clean;compile"                                                                                                                                                                                          [19:36:17]
[info] welcome to sbt 1.5.5 (Oracle Corporation Java 11.0.5)
[info] loading global plugins from /Users/dkichler/.sbt/1.0/plugins
[info] loading settings for project protoc-gen-test-build from plugins.sbt ...
[info] loading project definition from /Users/dkichler/projects/xxx/sandbox/protoc-gen-test/project
[info] loading settings for project root from build.sbt ...
[info] set current project to protoc-gen-example (in build file:/Users/dkichler/projects/xxx/sandbox/protoc-gen-test/)
[success] Total time: 0 s, completed Dec. 13, 2021, 7:36:29 p.m.
[info] Compiling 3 protobuf files to /Users/dkichler/projects/xxx/sandbox/protoc-gen-test/target/scala-2.13/src_managed/main/scalapb,/Users/dkichler/projects/xxx/sandbox/protoc-gen-test/target/scala-2.13/src_managed/main/java,/Users/dkichler/projects/xxx/sandbox/protoc-gen-test/target/scala-2.13/src_managed/main
[info] compiling 4 Scala sources and 4 Java sources to /Users/dkichler/projects/xxx/sandbox/protoc-gen-test/target/scala-2.13/classes ...
[error] IO error while decoding /Users/dkichler/projects/xxx/sandbox/protoc-gen-test/target/scala-2.13/src_managed/main/sbt-buildinfo/BuildInfo.scala with UTF-8: /Users/dkichler/projects/xxx/sandbox/protoc-gen-test/target/scala-2.13/src_managed/main/sbt-buildinfo/BuildInfo.scala (No such file or directory)
[error] Please try specifying another one using the -encoding option
[error] one error found
[error] (Compile / compileIncremental) Compilation failed
[error] Total time: 5 s, completed Dec. 13, 2021, 7:36:34 p.m.
dkichler➜xxx/sandbox/protoc-gen-test(build-info-test✗)» sbt compile                                                                                                                                                                                                  [19:36:35]
[info] welcome to sbt 1.5.5 (Oracle Corporation Java 11.0.5)
[info] loading global plugins from /Users/dkichler/.sbt/1.0/plugins
[info] loading settings for project protoc-gen-test-build from plugins.sbt ...
[info] loading project definition from /Users/dkichler/projects/xxx/sandbox/protoc-gen-test/project
[info] loading settings for project root from build.sbt ...
[info] set current project to protoc-gen-example (in build file:/Users/dkichler/projects/xxx/sandbox/protoc-gen-test/)
[info] Executing in batch mode. For better performance use sbt's shell
[info] compiling 4 Scala sources and 4 Java sources to /Users/dkichler/projects/xxx/sandbox/protoc-gen-test/target/scala-2.13/classes ...
[success] Total time: 6 s, completed Dec. 13, 2021, 7:37:12 p.m.
```

Expected behaviour can also be demonstrated by explicitly invoking `managedSources` prior to an initial compile:

```
$> sbt "clean;managedSources;compile"
```
