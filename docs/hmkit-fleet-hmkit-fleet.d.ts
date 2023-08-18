type Nullable<T> = T | null | undefined
export declare class HMKitFleet {
    constructor(configuration: string, environment?: HMKitFleet.Environment, hmKitConfiguration?: any/* HMKitConfiguration */);
    get environment(): HMKitFleet.Environment;
    test(): void;
    getEligibility(vin: string, brand: any/* Brand */): any/* Deferred<Response<EligibilityStatus>> */;
}
export declare namespace HMKitFleet {
    abstract class Environment {
        private constructor();
        static get PRODUCTION(): HMKitFleet.Environment & {
            get name(): "PRODUCTION";
            get ordinal(): 0;
        };
        static get SANDBOX(): HMKitFleet.Environment & {
            get name(): "SANDBOX";
            get ordinal(): 1;
        };
        static values(): Array<HMKitFleet.Environment>;
        static valueOf(value: string): HMKitFleet.Environment;
        get name(): "PRODUCTION" | "SANDBOX";
        get ordinal(): 0 | 1;
        static get Companion(): {
            get webUrl(): Nullable<string>;
            set webUrl(value: Nullable<string>);
        };
    }
}