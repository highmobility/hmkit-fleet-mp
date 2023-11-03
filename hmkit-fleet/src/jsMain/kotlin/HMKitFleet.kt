@file:OptIn(ExperimentalJsExport::class)
@file:JsExport

package com.highmobility.hmkitfleet

import com.highmobility.hmkitfleet.model.Brand
import com.highmobility.hmkitfleet.model.ClearanceStatus
import com.highmobility.hmkitfleet.model.ControlMeasure
import com.highmobility.hmkitfleet.model.Environment
import com.highmobility.hmkitfleet.model.RequestClearanceResponse
import com.highmobility.hmkitfleet.network.Response
import com.highmobility.hmkitfleet.network.UtilityRequests
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.promise
import kotlinx.serialization.json.Json.Default.configuration
import platform.Crypto
import kotlin.js.Promise
import kotlin.jvm.JvmOverloads

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
class HMKitFleet constructor(
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
  fun getEligibility(vin: String, brand: Brand): Any = scope.promise {
    koin.logger.debug("get js eligibility $vin $brand")
    koin.get<UtilityRequests>().getEligibility(vin, brand)
  }

  /**
   * Start the data access clearance process for a vehicle.
   *
   * @param vin The vehicle VIN number
   * @param brand The vehicle brand
   * @param controlMeasures Optional control measures for some vehicle brands.
   * @return The clearance status
   */

  fun requestClearance(
    vin: String,
    brand: Brand,
    controlMeasures: List<ControlMeasure>? = null
  ): Promise<Response<RequestClearanceResponse>> = scope.promise {
    TODO("")
  }

  /**
   * Get the status of VINs that have previously been registered for data access clearance with
   * [requestClearance]. After VIN is Approved, [getVehicleAccess] and subsequent [sendCommand]
   * can be sent.
   *
   * @return The clearance statuses
   */
  fun getClearanceStatuses(): Promise<Response<List<ClearanceStatus>>> = scope.promise {
    TODO("")
  }

  /**
   * Get the status of a [vin] that has previously been registered for data access clearance with
   * [requestClearance]. After the [vin] is Approved, [getVehicleAccess] and subsequent [sendCommand]
   * can be sent.
   *
   * @return The clearance status
   */
  fun getClearanceStatus(vin: String): Promise<Response<ClearanceStatus>> = scope.promise {
    TODO("")
  }

  /**
   * Delete the clearance for the given VIN.
   *
   * If the clearance is in a pending state, the activation process is canceled.
   * If the vehicle is in an approved state, a revoke is attempted. If the revoke is successful,
   * the [VehicleAccess] object for this VIN becomes invalid.
   *
   * @param vin The vehicle VIN number
   * @return The clearance status
   */
  fun deleteClearance(vin: String) = scope.promise {
    TODO("")
  }

  fun getVehicleState(
    vin: String
  ): Promise<Response<String>> = scope.promise {
    TODO("")
  }
}