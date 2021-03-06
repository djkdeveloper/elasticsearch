package com.djk.service;

import com.djk.bean.EsRequest;
import com.djk.bean.EsSearchRequest;

/**
 * Created by dujinkai on 16/10/1.
 */
public interface EsService {

    /**
     * 创建索引和mapping
     *
     * @return
     */
    int createIndexAndMapping(EsRequest esRequest);


    /**
     * 判断索引是否存在
     *
     * @param index
     * @return
     */
    boolean isIndexExist(String index);

    /**
     * 批量将数据插入es
     *
     * @param esRequest
     * @return
     */
    boolean batchAddDateToEs(EsRequest esRequest);

    /**
     * 根据id查询
     *
     * @param request
     * @return
     */
    String queryById(EsSearchRequest request);
}
