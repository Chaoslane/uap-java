/**
 * Copyright 2012 Twitter, Inc
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ua_parser;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Java implementation of <a href="https://github.com/tobie/ua-parser">UA Parser</a>
 *
 * @author Steve Jiang (@sjiang) <gh at iamsteve com>
 */
public class Parser {

    private static final String REGEX_YAML_PATH = "/regexes.yaml";
    private UserAgentParser uaParser;
    private OSParser osParser;
    private DeviceParser deviceParser;

    public Parser(){
        this(Parser.class.getResourceAsStream(REGEX_YAML_PATH));
    }

    public Parser(InputStream regexYaml) {
        initialize(regexYaml);
    }

    public Client parse(String agentString) {
        UserAgent ua = parseUserAgent(agentString);
        OS os = parseOS(agentString);
        Device device = parseDevice(agentString);
        return new Client(ua, os, device);
    }

    public UserAgent parseUserAgent(String agentString) {
        return uaParser.parse(agentString);
    }

    public Device parseDevice(String agentString) {
        return deviceParser.parse(agentString);
    }

    public OS parseOS(String agentString) {
        return osParser.parse(agentString);
    }

    private void initialize(InputStream regexYaml) {
        Yaml yaml = new Yaml(new SafeConstructor());
        @SuppressWarnings("unchecked")
        Map<String, List<Map<String, String>>> regexConfig = (Map<String, List<Map<String, String>>>) yaml.load(regexYaml);

        List<Map<String, String>> uaParserConfigs = regexConfig.get("user_agent_parsers");
        if (uaParserConfigs == null) {
            throw new IllegalArgumentException("user_agent_parsers is missing from yaml");
        }
        uaParser = UserAgentParser.fromList(uaParserConfigs);

        List<Map<String, String>> osParserConfigs = regexConfig.get("os_parsers");
        if (osParserConfigs == null) {
            throw new IllegalArgumentException("os_parsers is missing from yaml");
        }
        osParser = OSParser.fromList(osParserConfigs);

        List<Map<String, String>> deviceParserConfigs = regexConfig.get("device_parsers");
        if (deviceParserConfigs == null) {
            throw new IllegalArgumentException("device_parsers is missing from yaml");
        }
        deviceParser = DeviceParser.fromList(deviceParserConfigs);
    }

}
