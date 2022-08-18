Simple repo to demonstrate protoc plugin dependency resolution issue with sbt 1.5.x+

The scenario only seems to exists where there is a protobuf scoped `dependsOn` relation to another module, such as the relation between anotherMod -> base modules in build.sbt.

To demonstrate the broken resolution:

```
$> sbt clean compile
```

Observe the failure to resolve protoc dependency:

```
[error] lmcoursier.internal.shaded.coursier.error.FetchError$DownloadingArtifacts: Error fetching artifacts:
[error] https://repo1.maven.org/maven2/io/envoyproxy/protoc-gen-validate/protoc-gen-validate/0.6.1/protoc-gen-validate-0.6.1-osx-x86_64.protoc-plugin: not found: https://repo1.maven.org/maven2/io/envoyproxy/protoc-gen-validate/protoc-gen-validate/0.6.1/protoc-gen-validate-0.6.1-osx-x86_64.protoc-plugin
```

Expected behaviour can be observed by downgrading sbt version to 1.4.9 and running the same task as above.
