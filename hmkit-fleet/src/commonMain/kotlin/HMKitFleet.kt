package com.highmobility.hmkitfleet

import Environment
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

import com.highmobility.hmkitfleet.model.Brand
import com.highmobility.hmkitfleet.model.ClearanceStatus
import com.highmobility.hmkitfleet.model.ControlMeasure
import com.highmobility.hmkitfleet.model.EligibilityStatus
import com.highmobility.hmkitfleet.model.RequestClearanceResponse
import com.highmobility.hmkitfleet.network.Response
import com.highmobility.hmkitfleet.network.UtilityRequests
//import com.highmobility.hmkitfleet.network.ClearanceRequests
//import com.highmobility.hmkitfleet.network.Response
//import com.highmobility.hmkitfleet.network.UtilityRequests
//import com.highmobility.hmkitfleet.network.VehicleDataRequests
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.core.Koin
import kotlin.jvm.JvmField
import kotlin.jvm.JvmOverloads

//import kotlinx.coroutines.future.future
//import org.slf4j.Logger
//import java.util.concurrent.CompletableFuture

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
expect class HMKitFleet @JvmOverloads constructor(
    configuration: String,
    environment: Environment = Environment.PRODUCTION,
    hmKitConfiguration: HMKitConfiguration = HMKitConfiguration.defaultConfiguration()
) {
//    private val koin = Koin(configuration, environment, hmKitConfiguration).start()
//    private val scope = koin.get<CoroutineScope>()
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
    fun getEligibility(
        vin: String,
        brand: Brand,
    ): Any
}