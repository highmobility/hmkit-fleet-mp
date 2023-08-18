// import { HMKitFleet, Environment } from "hmkit-fleet" // types working
import { HMKitFleet, Environment } from "hmkit-fleet/kotlin/hmkit-fleet-mp-hmkit-fleet.mjs" // build working
import fs from "fs"

let privateKey = fs.readFileSync("private-key.json", "utf8")

let env =  Environment.SANDBOX

let hmkit = new HMKitFleet(privateKey, env)

hmkit.getEligibility("vin1", env).then((response) => {
  console.log(response)
})