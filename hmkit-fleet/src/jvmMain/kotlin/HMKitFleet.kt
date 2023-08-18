package com.highmobility.hmkitfleet

import Environment
import com.highmobility.hmkitfleet.model.Brand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import org.koin.core.Koin

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
//    private val logger by koin.inject<Logger>()

//    fun test() {
//        println("test")
//    }
    /**
     * Get the eligibility status for a specific VIN. This can be used to find out if the vehicle has the necessary
     * connectivity to transmit data.
     *
     * @param vin The vehicle VIN number
     * @param brand The vehicle brand
     * @return The eligibility status
     */
    actual fun getEligibility(vin: String, brand: Brand): Any = scope.future {
        println("get jvm Eligibility $vin $brand")

    }
}