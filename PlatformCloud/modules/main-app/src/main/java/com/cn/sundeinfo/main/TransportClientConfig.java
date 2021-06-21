package com.cn.sundeinfo.main;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.lang.System.out;

/**
 * 〈es连接器〉 弃用
 * ElasticSearch 8.0将正式弃用TransportClient连接器
 * ElasticSearch 7.0集群版测试连接失败-原因未知 端口9201设置无效 会自动拒绝
 * ElasticSearch 6.0版本可以完美兼容TransportClient连接器 如使用旧版本ElasticSearch
 * 打开当前类的Configuration注解及@Bean实例 进行引用
 *
 * @author shikai_men
 * @create 2021/5/27
 */
//@Configuration
public class TransportClientConfig {

    @Value("${es.host}")
    private String esHost;

    @Value("${es.port}")
    private int esPort;

    @Value("${es.name}")
    private String esName;


    @Bean
    public TransportClient esClient(){

        out.println("-------------");
        out.println(esName);

        // 1.构建setting配置
        Settings settings = Settings.builder()
                .put("cluster.name",esName)
//                .put("client.transport.sniff", false) //开启自动嗅探功能 默认不开启 开启会导致访问失败
                .build();

        // 2.创建一个客户端Client对象，需要在传入setting为empty
        TransportClient client = new PreBuiltTransportClient(settings);
        // 指定操作节点/ 集群多配
        try {
            client.addTransportAddress(new TransportAddress(InetAddress.getByName(esHost), esPort));
//            client.addTransportAddress(new TransportAddress(InetAddress.getByName(esHost), esPort));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // 3.使用client对象创建一个索引库，索引库名称为arm（前面为设置，get()为执行操作）
//        client.admin().indices().prepareCreate("arm").get();
        return client;
    }

}
