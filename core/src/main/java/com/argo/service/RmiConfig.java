package com.argo.service;

import com.argo.yaml.YamlTemplate;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.util.List;

/**
 * Created by yamingd on 9/10/15.
 */
@Scope("prototype")
public class RmiConfig {

    private static final String confName = "rmi.yaml";

    public static RmiConfig instance = null;

    /**
     * 加载配置信息
     * @throws IOException
     */
    public synchronized static void load() throws IOException {
        if (instance != null){
            return;
        }

        try {
            RmiConfig.instance = load(confName);
        } catch (IOException e) {
            RmiConfig.instance = new RmiConfig();
            throw e;
        }
    }

    /**
     * 加载配置信息
     * @throws IOException
     */
    public static RmiConfig load(String confName) throws IOException {
        RmiConfig config = YamlTemplate.load(RmiConfig.class, confName);
        return config;
    }

    private Integer asyncTrack = 300;
    private boolean enabled = false;
    private List<String> hosts;
    private Integer port = 10990;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getAsyncTrack() {
        return asyncTrack;
    }

    public void setAsyncTrack(Integer asyncTrack) {
        this.asyncTrack = asyncTrack;
    }
}
