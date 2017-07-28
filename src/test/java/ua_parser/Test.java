package ua_parser;

import java.io.IOException;

/**
 * Created by 43890 on 2017/2/12.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        String uaString = "HbbTV/1.1.1 (;Samsung;SmartTV2013;T-FXPDEUC-1102.2;;) WebKit";
        System.out.println(uaString);
        Parser uaParser = CachingParser.getInstance();
        Client c = uaParser.parse(uaString);

        System.out.println(c.userAgent.family); // => "Mobile Safari"
        System.out.println(c.userAgent.major);  // => "5"
        System.out.println(c.userAgent.minor);  // => "1"

        System.out.println(c.os.family);        // => "iOS"
        System.out.println(c.os.major);         // => "5"
        System.out.println(c.os.minor);         // => "1"

        System.out.println(c.device.family);    // => "iPhone"
        System.out.println(c.device.brand);
        System.out.println(c.device.model);
    }
}
