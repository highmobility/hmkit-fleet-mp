package com.highmobility.hmkitfleet

import com.highmobility.hmkitfleet.model.Brand
import com.highmobility.hmkitfleet.model.EligibilityStatus
import com.highmobility.hmkitfleet.model.Environment
import com.highmobility.hmkitfleet.network.Response
import com.highmobility.hmkitfleet.network.UtilityRequests
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

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

class HMKitFleet @JvmOverloads constructor(
  configuration: String,
  environment: Environment,
  hmKitConfiguration: HMKitConfiguration = HMKitConfiguration.defaultConfiguration()
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
  fun getEligibility(vin: String, brand: Brand): CompletableFuture<Response<EligibilityStatus>> = scope.future {
    koin.get<UtilityRequests>().getEligibility(vin, brand)
  }
}