import com.highmobility.hmkitfleet.HMKitConfiguration;
import com.highmobility.hmkitfleet.HMKitFleet;
import com.highmobility.hmkitfleet.model.Environment;
import com.highmobility.hmkitfleet.model.Brand;
import com.highmobility.hmkitfleet.model.EligibilityStatus;
import com.highmobility.hmkitfleet.network.Response;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

class Main {
  public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
    // read string from resources
    String configurationFile = Main.class.getResource("private-key.json").getFile();
    String configuration = new String(Files.readAllBytes(Paths.get(configurationFile)));

    HMKitConfiguration.Builder hmkitConfigBuilder = new HMKitConfiguration.Builder();
    addCustomOkHttpClient(hmkitConfigBuilder);
    HMKitConfiguration hmkitConfig = hmkitConfigBuilder.build();

    HMKitFleet hmkit = new HMKitFleet(
      configuration,
      Environment.SANDBOX,
      hmkitConfig
    );

    Response<EligibilityStatus> response = hmkit.getEligibility("1HMGT3T163YC5RS2D", Brand.SANDBOX).get();

    System.out.println("consumer: response " + response.getResponse());
  }

  static HMKitConfiguration.Builder addCustomOkHttpClient(HMKitConfiguration.Builder builder) {
    OkHttpClient custom = new OkHttpClient.Builder()
      .addInterceptor(chain -> {
        System.out.println("intercept OkHttp");
        return chain.proceed(chain.request());
      })
      .build();

    builder.client(custom);

    return builder;
  }
}


