@file:OptIn(ExperimentalJsExport::class)
@file:JsExport()

package com.highmobility.hmkitfleet

import Environment
import com.highmobility.hmkitfleet.model.Brand
import com.highmobility.hmkitfleet.network.UtilityRequests
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.promise
import kotlinx.serialization.json.Json.Default.configuration

/**
 * HMKitFleet is the access point for the Fleet SDK functionality. It is accessed by
 * creating a new HMKitFleet object with a service account private key JSON string.
 *
 * ```
 * HMKitFleet fleet = new HMKitFleet(
 *     readStringContents(service-account-private-key-{id}.json),
 *     HMKitFleet.Environment.SANDBOX
 * );
 * ```
 */
actual class HMKitFleet actual constructor(
    configuration: String,
    environment: Environment,
    hmKitConfiguration: HMKitConfiguration
) {
    private val koin = Koin(configuration, environment, hmKitConfiguration).start()
    private val scope = koin.get<CoroutineScope>()

    /**
     * Get the eligibility status for a specific VIN. This can be used to find out if the vehicle has the necessary
     * connectivity to transmit data.
     *
     * @param vin The vehicle VIN number
     * @param brand The vehicle brand
     * @return The eligibility status
     */
    actual fun getEligibility(vin: String, brand: Brand):Any = scope.promise {
        println("get js eligibility $vin $brand")

        println("starting job")
        val job = async {
            for (i in 1..10) {
                print(".")
                delay(100)
            }
            println("done")
        }

        job.await()
//        koin.get<UtilityRequests>().getEligibility(vin, brand)
    }
}