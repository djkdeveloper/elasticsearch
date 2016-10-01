package com.djk.service.impl;

import com.djk.bean.ESClientManager;
import com.djk.bean.EsDocument;
import com.djk.bean.EsRequest;
import com.djk.service.EsService;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dujinkai on 16/10/1.
 */
@Service
public class EsServiceImpl implements EsService {


    @Autowired
    private ESClientManager esClientManager;

    /**
     * 创建索引和类型
     *
     * @param esRequest
     * @return
     */
    public int createIndexAndMapping(EsRequest esRequest) {

        if (!esRequest.validate()) {
            return -1;
        }

        // 如果索引存在 则先删除索引
        if (isIndexExist(esRequest.getIndex())) {
            esClientManager.deleteIndex(esRequest.getIndex());
        }

        // 创建索引
        esClientManager.createIndex(esRequest.getIndex());

        // 如果mapping 存在就删除mapping
        if (esClientManager.isMappingExist(esRequest.getIndex(), esRequest.getMapping())) {
            esClientManager.deleteMapping(esRequest.getIndex(), esRequest.getMapping());
        }

        try {
            // 创建mapping
            esClientManager.createMapping(esRequest.getIndex(), esRequest.getMapping(), getXContentBuilder());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 判断索引是否存在
     *
     * @param index
     * @return
     */
    public boolean isIndexExist(String index) {
        if (StringUtils.isEmpty(index)) {
            return false;
        }
        return esClientManager.isIndexExist(index);
    }


    /**
     * 批量将数据插入es
     *
     * @param esRequest
     * @return
     */
    public boolean batchAddDateToEs(EsRequest esRequest) {
        return esClientManager.batchAddDateToEs(esRequest.getIndex(), esRequest.getMapping(), getIndexRequest(esRequest.getEsDocuments()));
    }


    /*****************************************************************************************************
     *                                                                                                   *
     *                                     华丽的分割线                                                    *
     *                                                                                                   *
     *****************************************************************************************************/

    /**
     * 获得es的request集合
     *
     * @param esDocuments
     * @return
     */
    private List<IndexRequest> getIndexRequest(List<EsDocument> esDocuments) {
        if (CollectionUtils.isEmpty(esDocuments)) {
            return new ArrayList<>();
        }
        return esDocuments.stream().map(esDocument -> esClientManager.getIndexRequest(esDocument)).collect(Collectors.toList());
    }


    /**
     * 构造mapping 映射
     *
     * @return
     * @throws IOException
     */
    private XContentBuilder getXContentBuilder() throws IOException {
        XContentBuilder mapping = XContentFactory.jsonBuilder().startObject();
        mapping.startObject("properties");

        mapping.startObject("id").field("type", "long").field("store", "yes").endObject();
        mapping.startObject("name").field("type", "string").endObject();
        mapping.startObject("price").field("type", "double").endObject();
        mapping.startObject("cate").field("type", "string").field("index", "not_analyzed");
        mapping.endObject().endObject();
        return mapping;
    }


}
