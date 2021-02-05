package com.thinkit.cloud.springbootes.util.es.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * ES的ClientFactory
 */
public class ClientFactory {
    // 单例大法好
    private static volatile RestHighLevelClient restHighLevelClient;
    
    private ClientFactory() {
        throw new IllegalStateException("Utility class");
     }
    
    public static RestHighLevelClient getRestHighLevelClient(String ip, Integer port) {
        if (restHighLevelClient == null) {
            synchronized (RestHighLevelClient.class) {
                if (restHighLevelClient == null) {
                    restHighLevelClient = new RestHighLevelClient(
                            RestClient.builder(new HttpHost(ip, port, "http"))
                                    .setRequestConfigCallback(requestConfigBuilder -> {
                                        requestConfigBuilder
                                                .setConnectTimeout(5000)
                                                .setSocketTimeout(60000);
                                        return requestConfigBuilder;
                                    })
                                    .setMaxRetryTimeoutMillis(60000)
                    );
                }
            }
        }
        return restHighLevelClient;
    }
    
}
