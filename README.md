Simple repo to demonstrate protoc plugin dependency resolution issue with sbt 1.5.x+

Expected behaviour is demonstrated against tag `resolution-works`, using sbt 1.4.9

To show expected behaviour:

```
$> sbt "show anotherMod/protobuf:managedClasspath"
```

Observe the dependency
`io/envoyproxy/protoc-gen-validate/protoc-gen-validate/0.6.1/protoc-gen-validate-0.6.1-osx-x86_64.exe`
in the resulting report.

To reproduce the resolution issue, checkout tag `resolution-broken` (which is on sbt 1.5.0) and run the same command as above.

Observe failure to resolve the same dependency, using an incorrect extension of `.protoc-plugin`
