/*
package com.cn.sundeinfo.core.elasticsearch;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.cn.sundeinfo.core.elasticsearch.common.CommonFunction;
import com.cn.sundeinfo.core.elasticsearch.model.*;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

@Component
public class ElasticExecutor2 {
    final String type = "_doc";
    @Autowired(
            required = false
    )
    RestHighLevelClient restHighLevelClient;
    @Value("${spring.data.elasticsearch.shards:}")
    Integer number_of_shards;
    @Value("${spring.data.elasticsearch.replicas:}")
    Integer number_of_replicas;
    @Value("${spring.data.elasticsearch.batch:}")
    Integer batch_size;

    public ElasticExecutor2() {
    }

    public void createIndex() throws Exception {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources("classpath*:idx/*.json");
        Resource resources2 = resourcePatternResolver.getResource("classpath:setting/setting.json");


        String name2 = resources2.getFilename().substring(0, resources2.getFilename().lastIndexOf("."));
        String settings = IOUtils.toString(resources2.getInputStream());

        Resource[] var3 = resources;
        int var4 = resources.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Resource res = var3[var5];
            String name = res.getFilename().substring(0, res.getFilename().lastIndexOf("."));
            String json = IOUtils.toString(res.getInputStream());
            this.createIndex(name, json, settings);
        }

    }

    public boolean createIndex(String idxName, String idxSQL, String setting) throws Exception {
        if (this.indexExist(idxName)) {
            return false;
        } else {
            CreateIndexRequest request = new CreateIndexRequest(idxName);
            this.buildSetting(request);
            request.settings(setting, XContentType.JSON);
            request.mapping(idxSQL, XContentType.JSON);
            CreateIndexResponse res = this.restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            return res.isAcknowledged();
        }
    }

    public boolean indexExist(String idxName) throws Exception {
        GetIndexRequest request = new GetIndexRequest(new String[]{idxName});
        return this.restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
    }

    public void buildSetting(CreateIndexRequest request) {
        request.settings(Settings.builder().put("index.number_of_shards", this.number_of_shards).put("index.number_of_replicas", this.number_of_replicas));
    }

    public <T extends ElasticContent> void insertOrUpdateOne(String idxName, ElasticBaseEntity<T> entity) throws IOException {
        IndexRequest request = new IndexRequest(idxName);
        request.id(entity.getId());
        XContentBuilder builder = this.bindQueryBuilder(entity.getBody());
        request.source(builder);
        this.restHighLevelClient.index(request, RequestOptions.DEFAULT);
    }

    public <T extends ElasticContent> T insert(String idxName, T entity) throws IOException {
        ElasticBaseEntity elasticBaseEntity = new ElasticBaseEntity();
        entity.setId(CommonFunction.CreateUUIDString());
        elasticBaseEntity.setBody(entity);
        this.insertOrUpdateOne(idxName, elasticBaseEntity);
        return entity;
    }

    public <T> XContentBuilder bindQueryBuilder(T entity) throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        Map en = ConvertEntity.CovertToMap(entity);
        builder.startObject();
        this.convertMapToBuilder(en, builder);
        builder.endObject();
        return builder;
    }

    protected void convertMapToBuilder(Map<String, Object> map, XContentBuilder builder) throws IOException {
        Iterator var3 = map.keySet().iterator();

        while(var3.hasNext()) {
            String key = (String)var3.next();
            if (map.get(key) instanceof Map) {
                builder.startObject(key);
                this.convertMapToBuilder((Map)map.get(key), builder);
                builder.endObject();
            } else if (map.get(key) instanceof List) {
                builder.startArray(key);
                this.convertListToBuilder(key, (List)map.get(key), builder);
                builder.endArray();
            } else {
                builder.field(key, map.get(key));
            }
        }

    }

    protected void convertListToBuilder(String key, List list, XContentBuilder builder) throws IOException {
        Iterator var4 = list.iterator();

        while(var4.hasNext()) {
            Object m = var4.next();
            if (m instanceof Map) {
                builder.map((Map)m);
            } else if (m instanceof List) {
                this.convertListToBuilder(key, (List)m, builder);
            } else {
                builder.value(m);
            }
        }

    }

    public void insertBatch(String idxName, List<ElasticBaseEntity> list) throws IOException {
        BulkRequest request = new BulkRequest();
        list.forEach((item) -> {
            request.add((new IndexRequest(idxName)).id(item.getId()).source(new Object[]{item.getBody(), XContentType.JSON}));
        });
        this.restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
    }

    public <T> void deleteBatch(String idxName, Collection<T> idList) throws IOException {
        BulkRequest request = new BulkRequest();
        idList.forEach((item) -> {
            request.add(new DeleteRequest(idxName, "_doc", item.toString()));
        });
        this.restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
    }

    public <T> ElasticResult<T> search(String idxName, SearchSourceBuilder builder, Class<T> c) throws IOException {
        SearchRequest request = new SearchRequest(new String[]{idxName});
        request.source(builder);
        request.searchType("dfs_query_then_fetch");
        SearchResponse response = this.restHighLevelClient.search(request, RequestOptions.DEFAULT);
        SearchHits search = response.getHits();
        long total = search.getTotalHits().value;
        SearchHit[] hits = search.getHits();
        List<T> res = new ArrayList(hits.length);
        SearchHit[] var11 = hits;
        int var12 = hits.length;

        for(int var13 = 0; var13 < var12; ++var13) {
            SearchHit hit = var11[var13];
            res.add(ConvertEntity.jsonToJava(hit.getSourceAsString(), c));
        }

        ElasticResult<T> resultBase = new ElasticResult();
        resultBase.setTotle(total);
        resultBase.setRows(res);
        return resultBase;
    }

    protected void createChild(AggerItem aggs, AggregationBuilder agg) {
        Iterator var3 = aggs.getChilds().iterator();

        while(var3.hasNext()) {
            AggerItem it = (AggerItem)var3.next();
            AggregationBuilder aggregationBuilder = agg.subAggregation(AggregationBuilders.terms(it.getName()).field(it.getField()));
            this.createChild(it, aggregationBuilder);
        }

    }

    protected void readAgger(AggerItem item, Terms terms) {
        Iterator var3 = terms.getBuckets().iterator();

        while(var3.hasNext()) {
            Bucket bucket = (Bucket)var3.next();
            Iterator var5 = item.getChilds().iterator();

            while(var5.hasNext()) {
                AggerItem it = (AggerItem)var5.next();
                AggerItem sub = new AggerItem();
                Terms sub_terms = (Terms)bucket.getAggregations().get(it.getName());
                sub.setName(it.getName());
                sub.setField(it.getField());
                sub.setKey(bucket.getKeyAsString());
                sub.setValue(bucket.getDocCount());
                item.getResults().add(sub);
                this.readAgger(it, sub_terms);
            }

            AggerItem agg = new AggerItem();
            agg.setName(item.getName());
            agg.setField(item.getField());
            agg.setKey(bucket.getKeyAsString());
            agg.setValue(bucket.getDocCount());
            item.getResults().add(agg);
        }

    }

    public <T extends QueryBase> List<AggerItem> aggreCount(String idxName, String path, T builder) throws Exception {
        String json = CommonFunction.ReadFileAll(path, "utf-8");
        Type tp = (new TypeToken<List<AggerSetting>>() {
        }).getType();
        List<AggerSetting> setting = (List)ConvertEntity.jsonToJava(json, tp);
        List<AggerItem> list = new ArrayList();
        Iterator var8 = setting.iterator();

        while(var8.hasNext()) {
            AggerSetting agg = (AggerSetting)var8.next();
            AggerItem item = new AggerItem();
            item.setName(agg.getName());
            item.setField(agg.getField());
            Iterator var11 = agg.getChild().iterator();

            while(var11.hasNext()) {
                String s = (String)var11.next();
                AggerItem sub = new AggerItem();
                sub.setName(s);
                sub.setField(s);
                item.getChilds().add(sub);
            }

            list.add(item);
        }

        return this.aggreCount(idxName, (List)list, (QueryBase)builder);
    }

    public <T extends QueryBase> List<AggerItem> aggreCount(String idxName, List<AggerItem> aggItems, T builder) throws Exception {
        Class<?> b = builder.getClass();
        QueryConvert convert = new QueryConvert();
        ConvertEntity.copyEntity(builder, b, convert);
        SearchSourceBuilder query = convert.builder();
        return this.aggreCount(idxName, aggItems, query);
    }

    protected List<AggerItem> aggreCount(String idxName, List<AggerItem> aggItems, SearchSourceBuilder query) throws Exception {
        SearchRequest request = new SearchRequest(new String[]{idxName});
        Iterator var5 = aggItems.iterator();

        while(var5.hasNext()) {
            AggerItem agg = (AggerItem)var5.next();
            AggregationBuilder build = ((TermsAggregationBuilder)AggregationBuilders.terms(agg.getName()).field(agg.getField())).size(100);
            this.createChild(agg, build);
            query.aggregation(build).size(100);
        }

        request.source(query);
        SearchResponse response = this.restHighLevelClient.search(request, RequestOptions.DEFAULT);
        Iterator var10 = aggItems.iterator();

        while(var10.hasNext()) {
            AggerItem agg = (AggerItem)var10.next();
            Terms terms = (Terms)response.getAggregations().get(agg.getName());
            this.readAgger(agg, terms);
        }

        return aggItems;
    }

    public <T extends QueryBase> List<AggerItem> aggreCount(String idxName, String path, MultiQuery<T> builder) throws Exception {
        String json = CommonFunction.ReadFileAll(path, "utf-8");
        Type tp = (new TypeToken<List<AggerSetting>>() {
        }).getType();
        List<AggerSetting> setting = (List)ConvertEntity.jsonToJava(json, tp);
        List<AggerItem> list = new ArrayList();
        Iterator var8 = setting.iterator();

        while(var8.hasNext()) {
            AggerSetting agg = (AggerSetting)var8.next();
            AggerItem item = new AggerItem();
            item.setName(agg.getName());
            item.setField(agg.getField());
            Iterator var11 = agg.getChild().iterator();

            while(var11.hasNext()) {
                String s = (String)var11.next();
                AggerItem sub = new AggerItem();
                sub.setName(s);
                sub.setField(s);
                item.getChilds().add(sub);
            }

            list.add(item);
        }

        return this.aggreCount(idxName, (List)list, (SearchSourceBuilder)this.createSearchBuilder(builder));
    }

    public TokensItem pretty(String type, List<String> strs) throws IOException {
        Request request = new Request("GET", "_analyze");
        Map<String, Object> mp = new TreeMap();
        mp.put("analyzer", type);
        mp.put("text", strs);
        String json = ConvertEntity.javaToJson(mp);
        request.setJsonEntity(json);
        Response response = this.restHighLevelClient.getLowLevelClient().performRequest(request);
        TokensItem tokens = (TokensItem)ConvertEntity.jsonToJava(EntityUtils.toString(response.getEntity()), TokensItem.class);
        return tokens;
    }

    public <T, V extends QueryBase> ElasticResult<T> search(String idxName, V builder, Class<T> c) throws Exception {
        Class<?> b = builder.getClass();
        QueryConvert query = new QueryConvert();
        ConvertEntity.copyEntity(builder, b, query);
        SearchSourceBuilder bd = query.builder();
        if (null != builder.getPages()) {
            bd.from(builder.getPages().getFrom());
            bd.size(builder.getPages().getSize());
            if (null != builder.getPages().getSort()) {
                Iterator var7 = builder.getPages().getSort().keySet().iterator();

                while(var7.hasNext()) {
                    String k = (String)var7.next();
                    bd.sort(k, "DESC".equalsIgnoreCase((String)builder.getPages().getSort().get(k)) ? SortOrder.DESC : SortOrder.ASC);
                }

                bd.sort("_score");
            }
        }

        bd.trackTotalHits(true);
        return this.search(idxName, bd, c);
    }

    private <T extends QueryBase> SearchSourceBuilder createSearchBuilder(MultiQuery<T> builder) throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        BoolQueryBuilder boolFilterBuilder = new BoolQueryBuilder();
        List<BoolQueryBuilder> nextQueryBuilder = new ArrayList();
        List<BoolQueryBuilder> nextFilterBuilder = new ArrayList();
        Iterator var7 = builder.getItems().iterator();
        BoolQueryBuilder tmpMust = new BoolQueryBuilder();
        BoolQueryBuilder tmpShould = new BoolQueryBuilder();
        List<BoolQueryBuilder> tmpShoulds = new ArrayList<>();
        BoolQueryBuilder tmpMustFilter = new BoolQueryBuilder();
        BoolQueryBuilder tmpShouldFilter = new BoolQueryBuilder();
        BoolQueryBuilder tmpNextMust = new BoolQueryBuilder();
        BoolQueryBuilder tmpNextShould = new BoolQueryBuilder();
        List<BoolQueryBuilder> tmpNextShoulds = new ArrayList<>();
        while(var7.hasNext()) {
            SearchListBase<T> it = (SearchListBase)var7.next();
            Class<?> b = ((QueryBase)it.getItem()).getClass();
            QueryConvert query = new QueryConvert();
            ConvertEntity.copyEntity(it.getItem(), b, query);
            if ("query".equals(it.getSearchType())) {
                if ("must".equals(it.getOper())) {
//                    BoolQueryBuilder tmp = new BoolQueryBuilder();
//                    boolQueryBuilder.should(tmp.must(query.boolQueryBuilder));
                    tmpMust.must(query.boolQueryBuilder);
                    ResourceTitleQuery resourceTitleQuery = (ResourceTitleQuery) it.getItem();
                    if (resourceTitleQuery.getAuths() != null || resourceTitleQuery.getInnerTypes() != null){
                        tmpShould.must(query.boolQueryBuilder);
                    }
                } else if ("should".equals(it.getOper())) {
//                    BoolQueryBuilder tmp = new BoolQueryBuilder();
//                    boolQueryBuilder.should(tmp.must(query.boolQueryBuilder));
                    tmpShoulds.add(new BoolQueryBuilder().must(query.boolQueryBuilder));
//                    tmpShould.must(query.boolQueryBuilder);
                } else if ("must_not".equals(it.getOper())) {
                    boolQueryBuilder.mustNot(query.boolQueryBuilder);
                } else if (it.getOper() != null && it.getOper().startsWith("next_")) {
                    String oper = it.getOper().replace("next_", "");
//                    tmpMust.must(query.boolQueryBuilder);
                    if ("must".equals(oper)){
                        tmpNextMust.must(query.boolQueryBuilder);
                    }else if ("should".equals(oper)) {
                        tmpNextShoulds.add(new BoolQueryBuilder().must(query.boolQueryBuilder));
//                        tmpNextShould.must(query.boolQueryBuilder);
                    }else if ("must_not".equals(oper)) {
                        boolQueryBuilder.mustNot(query.boolQueryBuilder);
                    }
                }else{
                    nextQueryBuilder.add(query.boolQueryBuilder);
                }
            } else if ("filter".equals(it.getSearchType())) {
                if ("must".equals(it.getOper())) {
//                    boolFilterBuilder.must(query.boolFilterBuilder);

                    tmpMustFilter.must(query.boolFilterBuilder);
                    ResourceTitleQuery resourceTitleQuery = (ResourceTitleQuery) it.getItem();
                    if (resourceTitleQuery.getAuths() != null || resourceTitleQuery.getInnerTypes() != null){
                        tmpMustFilter.must(query.boolFilterBuilder);
                    }
                } else if ("should".equals(it.getOper())) {
//                    boolFilterBuilder.should(query.boolFilterBuilder);
                    tmpShouldFilter.must(query.boolFilterBuilder);
                } else if ("must_not".equals(it.getOper())) {
                    boolFilterBuilder.mustNot(query.boolFilterBuilder);
                } else if ("next".equals(it.getOper())) {
                    nextFilterBuilder.add(query.boolFilterBuilder);
                }
            }
        }

        BoolQueryBuilder tmpNext = new BoolQueryBuilder();
        if (tmpNextMust.must().size() > 0){
            tmpNext.should(tmpNextMust);
        }

        for (BoolQueryBuilder b : tmpNextShoulds){
            tmpNext.should(b);
        }
//        if (tmpNextShould.must().size() > 0){
//            tmpNext.should(tmpNextShould);
//        }

        if (tmpNext.should().size() > 0){
            tmpMust.must(tmpNext);
            tmpShould.must(tmpNext);
        }

        boolQueryBuilder.should(tmpMust);

        for (BoolQueryBuilder b : tmpShoulds){
            for (QueryBuilder tb : tmpShould.must()){
                b.must(tb);
            }
            boolQueryBuilder.should(b);
        }
//        if (tmpShould.must().size() > 2){
//            boolQueryBuilder.should(tmpShould);
//        }
        if (tmpMustFilter.must().size() > 0){
            boolFilterBuilder.should(tmpMustFilter);
        }
        if (tmpShouldFilter.must().size() > 0){
            boolFilterBuilder.should(tmpShouldFilter);
        }


        BoolQueryBuilder q = boolQueryBuilder;

        BoolQueryBuilder next;
        for(Iterator var13 = nextQueryBuilder.iterator(); var13.hasNext(); q = next) {
            BoolQueryBuilder next2 = (BoolQueryBuilder)var13.next();
            next = new BoolQueryBuilder();
            next.must(q);
            next.must(next2);
        }

        BoolQueryBuilder f = boolFilterBuilder;

        BoolQueryBuilder n;
        Iterator var16;
        for(var16 = nextFilterBuilder.iterator(); var16.hasNext(); f = n) {
            next = (BoolQueryBuilder)var16.next();
            n = new BoolQueryBuilder();
            n.must(f);
            n.must(next);
        }

        searchSourceBuilder.query(q);
        searchSourceBuilder.postFilter(f);
        if (null != builder.getPages()) {
            searchSourceBuilder.from(builder.getPages().getFrom());
            searchSourceBuilder.size(builder.getPages().getSize());
            if (null != builder.getPages().getSort()) {
                var16 = builder.getPages().getSort().keySet().iterator();

                while(var16.hasNext()) {
                    String k = (String)var16.next();
                    if ("_score".equalsIgnoreCase(k)) {
                        searchSourceBuilder.sort("_score");
                    } else {
                        searchSourceBuilder.sort(k, "desc".equalsIgnoreCase((String)builder.getPages().getSort().get(k)) ? SortOrder.DESC : SortOrder.ASC);
                    }
                }
            }
        }

        searchSourceBuilder.trackTotalHits(true);
        return searchSourceBuilder;
    }

    public <T, V extends QueryBase> ElasticResult<T> search(String idxName, MultiQuery<V> builder, Class<T> c) throws Exception {
        return this.search(idxName, this.createSearchBuilder(builder), c);
    }

    public boolean deleteIndex(String idxName) throws Exception {
        if (!this.indexExist(idxName)) {
            return false;
        } else {
            this.restHighLevelClient.indices().delete(new DeleteIndexRequest(idxName), RequestOptions.DEFAULT);
            return true;
        }
    }

    public void deleteByQuery(String idxName, QueryBuilder builder) throws IOException {
        DeleteByQueryRequest request = new DeleteByQueryRequest(new String[]{idxName});
        request.setQuery(builder);
        request.setBatchSize(this.batch_size);
        request.setConflicts("proceed");
        this.restHighLevelClient.deleteByQuery(request, RequestOptions.DEFAULT);
    }
}

*/
