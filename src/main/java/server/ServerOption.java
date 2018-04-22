package server;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

/**
 * Copyright © 2018 四月
 * Boil blood. All rights reserved.
 *
 * @Prject: ServerCluster-Java
 * @Package: server
 * @Description: ${todo}
 * @author: Boiling
 * @date: 2018/4/23 0023 1:11
 * @version: V1.0
 */
public class ServerOption {
    public ServerOption(String configPath) throws IOException, ParseException {
//        this.configPath = configPath;
        build();
    }
    private void build() throws IOException, ParseException {
        InputStream in = null;
        try {
//            in = FileLoaderUtil.findInputStreamByFileName(configPath);
//            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Properties properties = new Properties();
//            properties.load(in);

//            this.serverId = Cast.toInteger(properties.get("serverId"));
//            this.configDataPath = (String) properties.get("configDataPath");
//            this.gameDbConfigPath = (String) properties.get("gameDbConfigPath");
//            this.serverType = Cast.toInteger(properties.get("serverType"));
//            this.gameServerPort = Cast.toInteger(properties.get("gameServerPort"));
//            this.isDebug = Boolean.parseBoolean(properties.getProperty("isDebug"));
//            this.fcmCheck = Boolean.parseBoolean(properties.getProperty("fcmCheck"));
//            this.wgCheck = Boolean.parseBoolean(properties.getProperty("wgCheck"));
//            this.pushChat = Boolean.parseBoolean(properties.getProperty("pushChat"));
//            this.pushRole = Boolean.parseBoolean(properties.getProperty("pushRole"));
//            this.desKey = properties.getProperty("desKey");
//            this.gameServerPort = Integer.parseInt(properties.getProperty("gameServerPort"));
//            this.backServerPort = Integer.parseInt(properties.getProperty("backServerPort"));
//            this.httpServerPort = Integer.parseInt(properties.getProperty("httpServerPort"));
//            this.configDataPath = properties.getProperty("configDataPath");
//            this.platformId = Cast.toInteger(properties.getProperty("platformId"));


//            this.openTime = format.parse(properties.getProperty("openTime"));
//            String combineTime = "combineTime";
//            if (properties.getProperty(combineTime) != null) {
//                this.combineTime = format.parse(properties.getProperty(combineTime));
//            }
//
//            this.gameDbConfigPath = properties.getProperty("gameDbConfigPath");
//            this.logDBConfigPath = properties.getProperty("logDBConfigPath");
//            this.httpDBConfigPath = properties.getProperty("httpDBConfigPath");
//
//            this.fcmCheck = Boolean.parseBoolean(properties.getProperty("fcmCheck"));
//            this.wgCheck = Boolean.parseBoolean(properties.getProperty("wgCheck"));
//            this.pushChat = Boolean.parseBoolean(properties.getProperty("pushChat"));
//            this.pushRole = Boolean.parseBoolean(properties.getProperty("pushRole"));
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
