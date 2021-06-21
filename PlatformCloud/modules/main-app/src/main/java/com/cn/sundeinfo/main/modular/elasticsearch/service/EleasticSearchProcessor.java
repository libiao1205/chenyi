package com.cn.sundeinfo.main.modular.elasticsearch.service;

import com.cn.sundeinfo.main.modular.elasticsearch.entity.SearchCitions;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * 〈el搜索处理器〉
 *
 * @author shikai_men
 * @create 2021/5/29
 */
public interface EleasticSearchProcessor {
    public SearchSourceBuilder getSearchSourceBuilder(int from,int size);
    public SearchSourceBuilder getSearchSourceBuilder(int from,int size,int timeOut);
    public SearchResponse searchDataProcessor(SearchSourceBuilder searchSourceBuilder, SearchCitions citions,String[] includes,String _index) throws IOException;
    public void searchDataPrint(SearchResponse searchResponse);
    public void searchDataPrintByGroup(SearchResponse searchResponse,String[] group_field_arr);
    public SearchResponse searchPointHandle(SearchResponse searchResponse);
    public SearchResponse searchDataHandleByHighLight(SearchResponse search);

}
