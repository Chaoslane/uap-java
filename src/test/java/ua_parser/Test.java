package ua_parser;

import java.io.IOException;

/**
 * Created by 43890 on 2017/2/12.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        String uaString = "Mozilla/5.0+(Linux;+Android+6.0;+HUAWEI+NXT-AL10+Build/HUAWEINXT-AL10;+wv)+AppleWebKit/537.36+(KHTML,+like+Gecko)+Version/4.0+Chrome/45.0.2454.95+Mobile+Safari/537.36".replaceAll("\\+"," ");
//        String uaString="Mozilla/5.0+(iPhone+5SGLOBAL;+CPU+iPhone+OS+9_3_2+like+Mac+OS+X)+AppleWebKit/601.1.46+(KHTML,+like+Gecko)+Version/6.0+MQQBrowser/6.8+Mobile/13F69+Safari/8536.25+MttCustomUA/2";
        System.out.println(uaString);
        Parser uaParser = new Parser();
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
