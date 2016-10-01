package com.djk.bean;

import org.elasticsearch.common.lang3.StringUtils;

import java.util.List;

/**
 * Created by dujinkai on 16/10/1.
 */
public class EsRequest {

    /**
     * es的索引
     */
    private String index;

    /**
     * es的mapping信息
     */
    private String mapping;

    /**
     * 原数据
     */
    private List<EsDocument> esDocuments;

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public String getIndex() {
        return index;
    }

    public boolean validate() {
        return StringUtils.isNotEmpty(index);
    }


    public void setIndex(String index) {
        this.index = index;
    }

    public List<EsDocument> getEsDocuments() {
        return esDocuments;
    }

    public void setEsDocuments(List<EsDocument> esDocuments) {
        this.esDocuments = esDocuments;
    }

    @Override
    public String toString() {
        return "EsRequest{" +
                "index='" + index + '\'' +
                ", mapping='" + mapping + '\'' +
                ", esDocuments=" + esDocuments +
                '}';
    }
}
