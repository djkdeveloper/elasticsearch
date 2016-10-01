package com.djk.controller;

import com.alibaba.fastjson.JSON;
import com.djk.bean.EsDocument;
import com.djk.bean.EsRequest;
import com.djk.bean.Spu;
import com.djk.service.EsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by dujinkai on 16/10/1.
 */
@Controller
public class EsController {

    private static String[] cates;

    private static String[] names;

    static {
        cates = new String[]{"衣服", "台灯", "笔记本", "苹果电脑", "手机", "兵乓球", "跑步机", "宝马", "牛肉", "小说"};
        names = new String[]{"十一大促销商品", "大减价商品啊", "衣服了啊", "便宜卖了啊", "商品", "这个商品好啊", "挥泪大甩卖", "牛肉干啊", "好看的衣服", "不来后悔啊"};
    }

    @Resource
    private EsService esService;

    @RequestMapping("/createindexandmapping")
    @ResponseBody
    public int createMapping(EsRequest esRequest) {
        return esService.createIndexAndMapping(esRequest);
    }

    @RequestMapping("/isindexexist")
    @ResponseBody
    public boolean isIndexExist(String index) {
        return esService.isIndexExist(index);
    }

    @RequestMapping("/import")
    @ResponseBody
    public boolean batchDataToEs(EsRequest esRequest) {
        buildSpus(esRequest);
        return esService.batchAddDateToEs(esRequest);
    }


    /**
     * 构造模拟数据
     *
     * @param esRequest
     */
    private void buildSpus(EsRequest esRequest) {
        esRequest.setEsDocuments(getSpus().stream().map(spu -> EsDocument.build(esRequest.getIndex(), esRequest.getMapping(), JSON.toJSONString(spu), spu.getId())
        ).collect(Collectors.toList()));
    }

    /**
     * 获得模拟的商品数据
     *
     * @return
     */
    private List<Spu> getSpus() {
        return Stream.iterate(0L, num -> num + 1).limit(100).map(num -> Spu.build(num, getRandName(), getRandomPrice(), getRandomCate())).collect(Collectors.toList());
    }

    /**
     * 获得随记的价格
     *
     * @return
     */
    private Double getRandomPrice() {
        return Double.parseDouble(((int) (Math.random() * 100)) + "");
    }

    /**
     * 获得随记的分类
     *
     * @return
     */
    public String getRandomCate() {
        Random random = new Random();
        return cates[random.nextInt(10)];
    }

    /**
     * 获得随记的名称
     *
     * @return
     */
    public String getRandName() {
        Random random = new Random();
        return names[random.nextInt(10)] + getRandomCate();
    }

}
