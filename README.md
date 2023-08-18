## build java/js libs

### run js

build and run js:

`./gradlew assemble && cd hmkit-fleet-consumer-js && yarn && tsx ./src/index.ts && cd -`

- js will be located in `build/dist/js/productionLibrary/`
- java will be located in `build/libs/`

## positives

- Main purpose of sharing the http request logic with Coroutines can be exported to both JVM and JS.

Coroutines work. Ktor http client works.

- has code completions with Typescript .d.ts definitions
    - can generate TS docs from this

```typescript
// import { HMKitFleet, Environment } from "hmkit-fleet" // types working
import { HMKitFleet, Environment } from "hmkit-fleet/kotlin/hmkit-fleet-mp-hmkit-fleet.mjs" // build working
import fs from "fs"

let privateKey = fs.readFileSync("private-key.json", "utf8")

let env =  Environment.SANDBOX

let hmkit = new HMKitFleet(privateKey, env)

hmkit.getEligibility("vin1", env).then((response) => {
  console.log(response)
})
```

![completions](./docs/completions.png)
check out generated types in [docs](./docs/hmkit-fleet-hmkit-fleet.d.ts)

## negatives

- if run into a problem, then it is probably a lot slower/impossible to resolve it. Jetbrains needs to update
  their multiplatform. For instance their TypeScript support is experimental, but Typescript has been around for a long
  time already.
- There is more overhead to manage 2 platforms
- need separate HMKitFleet classes for js and jvm. It's because Js has Promise return type and Java
  has CompletableFuture. These return types cannot be mixed in Kotlin

## debugging crashes

### js stack trace

```
➜ node index.js
file:///Users/tonis/workspace/java/hmkit-fleet-mp/hmkit-fleet-consumer-js/node_modules/hmkit-fleet/kotlinx-serialization-kotlinx-serialization-json.mjs:4748
  $l$loop: while (this.i3e_1 < source.length) {
                                      ^

TypeError: Cannot read properties of undefined (reading 'length')
   ...
    at ServiceAccountApiConfiguration_init_$Create$ (file:///Users/tonis/workspace/java/hmkit-fleet-mp/hmkit-fleet-consumer-js/node_modules/hmkit-fleet/hmkit-fleet-hmkit-fleet.mjs:539:10)
    at file:///Users/tonis/workspace/java/hmkit-fleet-mp/hmkit-fleet-consumer-js/node_modules/hmkit-fleet/hmkit-fleet-hmkit-fleet.mjs:367:25
```

this leads to Kotlin compiled js:

```javascript
function Koin$koinModules$lambda($configuration, $hmKitConfiguration, $environment) {
  return function ($this$module) {
L367    var configuration = ServiceAccountApiConfiguration_init_$Create$($configuration);
        var tmp$ret$2;
        // Inline function 'org.koin.core.module.Module.single' call
        var tmp0_single = Koin$koinModules$lambda$lambda($hmKitConfiguration);
        var tmp$ret$1;
        // Inline function 'org.koin.core.module._singleInstanceFactory' call
        var tmp0__singleInstanceFactory = Companion_getInstance_0().p3q_1;
```

The code is not clean, but it is readable and directs to the problem (ServiceAccoutApiConfiguration.kt init)

### js debugger

TODO

### java stack trace

TODO:

### java debugging

TODO:


### issues

currently cannot get both building and types working in js. I don't know how to map .mjs with types in vscode.