package com.cn.sundeinfo.main;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

import static java.lang.System.out;

/**
 * 〈es连接器〉
 *
 * @author shikai_men
 * @create 2021/5/27
 */
@Configuration
public class RestHighLevelClientConfig {

    @Value("${elasticSearch.hostName}")
    private String host;

    @Value("${elasticSearch.port}")
    private int port;

    @Value("${elasticSearch.scheme}")
    private String scheme;

    //长期保存bean对象 后期添加工厂构造
    @Bean("restClient")
    public RestHighLevelClient client() {
        return new RestHighLevelClient(
                RestClient.builder(//HttpHost... hosts原方法为可变数组 集群部署更改传参
                        new HttpHost(host, port, scheme)));
    }

}
