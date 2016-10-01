package com.djk.bean;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.mapping.delete.DeleteMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * Created by dujinkai on 16/10/1.
 */
public class ESClientManager {

    private TransportClient client;

    /**
     * 根据es 主机地址设置client
     *
     * @param hosts 主机地址字串
     */
    public ESClientManager(String hosts, String clusterName) {
        if (hosts == null || hosts.split(",").length == 0)
            throw new NullPointerException("es服务器地址空...");
        if (clusterName == null || "".equals(clusterName))
            throw new NullPointerException("es集群名称为空...");

        Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", clusterName).put("client.transport.sniff", true).build();
        client = new TransportClient(settings);
        String[] hostArr = hosts.split(",");
        for (String host : hostArr) {
            client.addTransportAddress(new InetSocketTransportAddress(host, 9300));
        }
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

        return client.admin().indices().exists(new IndicesExistsRequest(index)).actionGet().isExists();
    }

    /**
     * 创建索引
     * @param index
     * @return
     */
    public boolean createIndex(String index) {
        if (StringUtils.isEmpty(index)) {
            return false;
        }

        client.admin().indices().prepareCreate(index).execute().actionGet();
        return true;
    }

    /**
     * 删除索引
     *
     * @param index
     * @return
     */
    public boolean deleteIndex(String index) {
        if (StringUtils.isEmpty(index)) {
            return false;
        }

        client.admin().indices().prepareDelete(index).execute()
                .actionGet();
        return true;
    }

    /**
     * 判断mapping 是否存在
     *
     * @param index
     * @param mapping
     * @return
     */
    public boolean isMappingExist(String index, String mapping) {
        if (StringUtils.isEmpty(index) || StringUtils.isEmpty(mapping)) {
            return false;
        }
        return client.admin().indices().typesExists(new TypesExistsRequest(new String[]{index}, mapping)).actionGet().isExists();
    }

    /**
     * 删除mapping
     *
     * @param index
     * @param mapping
     * @return
     */
    public boolean deleteMapping(String index, String mapping) {
        if (StringUtils.isEmpty(index) || StringUtils.isEmpty(mapping)) {
            return false;
        }

        client.admin().indices().prepareDeleteMapping(index).setType(mapping).execute().actionGet();
        return true;
    }


    /**
     * 创建mapping
     *
     * @param index
     * @param mapping
     * @param xContentBuilder
     * @return
     */
    public boolean createMapping(String index, String mapping, XContentBuilder xContentBuilder) {
        if (StringUtils.isEmpty(index) || StringUtils.isEmpty(mapping) || Objects.isNull(xContentBuilder)) {
            return false;
        }
        return client.admin().indices().preparePutMapping(index).setType(mapping).setSource(xContentBuilder).execute().actionGet().isAcknowledged();
    }

    /**
     * 数据批量插入es
     *
     * @param index
     * @param mapping
     * @param requests
     * @return
     */
    public boolean batchAddDateToEs(String index, String mapping, List<IndexRequest> requests) {
        if (StringUtils.isEmpty(index) || StringUtils.isEmpty(mapping) || CollectionUtils.isEmpty(requests)) {
            return false;
        }
        BulkRequestBuilder bulkRequestBuilder = client
                .prepareBulk();

        requests.forEach(request -> bulkRequestBuilder.add(request));
        return !bulkRequestBuilder.execute()
                .actionGet().hasFailures();
    }

    /**
     * 获得es的request对象
     * @param esDocument
     * @return
     */
    public IndexRequest getIndexRequest(EsDocument esDocument) {
        return client
                .prepareIndex(esDocument.getIndex(), esDocument.getType(), esDocument.getId().toString())
                .setSource(esDocument.getJson())
                .request();
    }

    /**
     * 查询
     * @param index
     * @param type
     * @param queryBuilder
     * @return
     */
    public SearchResponse query(String index, String type, QueryBuilder queryBuilder) {
        return client.prepareSearch(index).setTypes(type).setQuery(queryBuilder).execute().actionGet();
    }


}
