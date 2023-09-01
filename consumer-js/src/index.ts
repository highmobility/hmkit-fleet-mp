// import { HMKitFleet, Environment } from "hmkit-fleet" // types working
// import { HMKitFleet, Environment } from "hmkit-fleet/kotlin/hmkit-fleet-mp-hmkit-fleet.mjs" // build working
import { HMKitFleet, Environment, Brand } from "hmkit-fleet/kotlin/hmkit-fleet-mp-hmkit-fleet.mjs"

import fs from "fs"
let privateKey = fs.readFileSync(new URL("private-key.json", import.meta.url)).toString()

let env =  Environment.SANDBOX
let hmkit = new HMKitFleet(privateKey, env)

let brand = Brand.SANDBOX
hmkit.getEligibility("1HMGT3T163YC5RS2D", brand).then((response) => {
  console.log(response)
})
