package com.djk.bean;

/**
 * Created by dujinkai on 16/10/1.
 */
public class EsSearchRequest {

    /**
     * 索引
     */
    private String index = "spuindex";

    /**
     * 类型
     */
    private String type = "spu";

    /**
     * 文档id
     */
    private String id;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
