package com.cn.sundeinfo.core.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.cn.sundeinfo.core.elasticsearch.common.CommonFunction;
import com.cn.sundeinfo.core.elasticsearch.model.*;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
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
public class ElasticExecutor {

    final String type = "_doc";

    public ElasticExecutor() {}

    public <T> ElasticResult<T> search(String idxName, SearchSourceBuilder builder) throws IOException {
        SearchRequest request = new SearchRequest(new String[]{idxName});
        Node node = new Node(new HttpHost("192.168.1.41", 9201));

        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(node));
        request.source(builder);
        request.searchType("dfs_query_then_fetch");
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits search = response.getHits();
        long total = search.getTotalHits().value;
        SearchHit[] hits = search.getHits();
        List<Map> res = new ArrayList(hits.length);
        SearchHit[] var11 = hits;
        int var12 = hits.length;

        for(int var13 = 0; var13 < var12; ++var13) {
            SearchHit hit = var11[var13];
            res.add(hit.getSourceAsMap());
//            res.add(ConvertEntity.jsonToJava(hit.getSourceAsString(), c));
        }

        ElasticResult<T> resultBase = new ElasticResult();
        resultBase.setTotle(total);
        resultBase.setRows(res);
        return resultBase;
    }
/*
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
    }*/

    public SearchSourceBuilder createSearchBuilder(MultiQuery builder) throws Exception {
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
            SearchListBase it = (SearchListBase)var7.next();
            BoolQueryBuilder query = new BoolQueryBuilder();
            bindOper(it, query);
            if ("query".equals(it.getSearchType())) {
                if ("must".equals(it.getSearchOper())) {
                    tmpMust.must(query);
                } else if ("should".equals(it.getOper())) {
                    tmpShoulds.add(new BoolQueryBuilder().must(query));
                } else if ("must_not".equals(it.getOper())) {
                    boolQueryBuilder.mustNot(query);
                } else if (it.getOper() != null && it.getOper().startsWith("next_")) {
                    String oper = it.getOper().replace("next_", "");
                    if ("must".equals(oper)){
                        tmpNextMust.must(query);
                    }else if ("should".equals(oper)) {
                        tmpNextShoulds.add(new BoolQueryBuilder().must(query));
//                        tmpNextShould.must(query);
                    }else if ("must_not".equals(oper)) {
                        boolQueryBuilder.mustNot(query);
                    }
                }else{
                    nextQueryBuilder.add(query);
                }
            } else if ("filter".equals(it.getSearchType())) {
                /*if ("must".equals(it.getOper())) {
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
                }*/
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

    void bindOper(SearchListBase base, BoolQueryBuilder bool) {
        BoolQueryBuilder boolQueryBuilder;
        Iterator var5;
        Object it;
        Iterator var7;

        if ("term".equals(base.getType())) {
            if ("must".equals(base.getOper())) {
                bool.must(QueryBuilders.termQuery(base.getKey(), base.getValue()).boost(base.getBoost()));
            } else if ("should".equals(base.getOper())) {
                bool.should(QueryBuilders.termQuery(base.getKey(), base.getValue()).boost(base.getBoost()));
            } else if ("must_not".equals(base.getOper())) {
                bool.mustNot(QueryBuilders.termQuery(base.getKey(), base.getValue()).boost(base.getBoost()));
            } else if ("range".equals(base.getOper()) && base.getRange() != null) {
                RangeItem range = base.getRange();
                RangeQueryBuilder rangeQueryBuilder = (RangeQueryBuilder)QueryBuilders.rangeQuery(base.getKey()).boost(base.getBoost());
                if (null != range.getFrom()) {
                    rangeQueryBuilder.from(range.getFrom().getValue());
                }

                if (null != range.getTo()) {
                    rangeQueryBuilder.to(range.getTo().getValue());
                }

                if ("should".equals(range.getLinkType())) {
                    bool.should(rangeQueryBuilder);
                } else if ("must_not".equals(range.getLinkType())) {
                    bool.mustNot(rangeQueryBuilder);
                } else {
                    bool.must(rangeQueryBuilder);
                }
            }
        } else if ("match".equals(base.getType())) {
            if ("must".equals(base.getOper())) {
                bool.must(QueryBuilders.matchQuery(base.getKey(), base.getValue()).analyzer("ik_smart").boost(base.getBoost()));
            } else if ("should".equals(base.getOper())) {
                bool.should(QueryBuilders.matchQuery(base.getKey(), base.getValue()).analyzer("ik_smart").boost(base.getBoost()));
            } else if ("must_not".equals(base.getOper())) {
                bool.mustNot(QueryBuilders.matchQuery(base.getKey(), base.getValue()).analyzer("ik_smart").boost(base.getBoost()));
            }
        }

    }
}
