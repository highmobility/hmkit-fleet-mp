# HMKit Fleet Multiplatform

## build/run java/js targets

### run js

build and run js:

`./gradlew assemble && cd hmkit-fleet-consumer-js && yarn && tsx ./src/index.ts && cd -`

continuous building - don't have to manually build on every change

`./gradle assemble --continuous`
in js folder: `yarn && tsx ./src/index.ts`

- js will be located in `build/dist/js/productionLibrary/`
- java will be located in `build/libs/`

### run jvm

`./gradlew :consumer-jvm:run `

### native

currently not implemented