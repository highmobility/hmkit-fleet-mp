import com.highmobility.hmkitfleet.HMKitFleet;
import com.highmobility.hmkitfleet.model.Environment;
import com.highmobility.hmkitfleet.model.Brand;
import com.highmobility.hmkitfleet.model.EligibilityStatus;
import com.highmobility.hmkitfleet.network.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

class Main {
  public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
    // read string from resources
    String configurationFile = Main.class.getResource("private-key.json").getFile();
    String configuration = new String(Files.readAllBytes(Paths.get(configurationFile)));

    HMKitFleet hmkit = new HMKitFleet(
      configuration,
      Environment.SANDBOX
    );

    Response<EligibilityStatus> response = hmkit.getEligibility("1HMGT3T163YC5RS2D", Brand.SANDBOX).get();

    // works
  }
}
