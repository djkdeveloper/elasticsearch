package com.djk.bean;

/**
 * Created by dujinkai on 16/10/1.
 */
public final class EsDocument {

    /**
     * 索引名称
     */
    private final String index;

    /**
     * 索引类型
     */
    private final String type;

    /**
     * 文档的json结构体
     */
    private final String json;

    /**
     * 文档id
     */
    private final Long id;

    private EsDocument(String index, String type, String json, Long id) {
        this.index = index;
        this.type = type;
        this.json = json;
        this.id = id;
    }

    /**
     * 构造文档实体类
     *
     * @param index
     * @param type
     * @param json
     * @return
     */
    public static EsDocument build(String index, String type, String json, Long id) {
        return new EsDocument(index, type, json, id);
    }

    public String getIndex() {
        return index;
    }

    public String getType() {
        return type;
    }

    public String getJson() {
        return json;
    }


    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "EsDocument{" +
                "index='" + index + '\'' +
                ", type='" + type + '\'' +
                ", json='" + json + '\'' +
                '}';
    }
}
