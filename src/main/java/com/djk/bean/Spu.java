package com.djk.bean;

/**
 * Created by dujinkai on 16/10/1.
 */
public final class Spu {

    private final Long id;

    private final String name;

    private final Double price;

    private final String cate;

    private Spu(Long id, String name, Double price, String cate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.cate = cate;
    }

    /**
     * 构造商品实体
     *
     * @param id
     * @param name
     * @param price
     * @param cate
     * @return
     */
    public static Spu build(Long id, String name, Double price, String cate) {
        return new Spu(id, name, price, cate);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getCate() {
        return cate;
    }

    @Override
    public String toString() {
        return "Spu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", cate='" + cate + '\'' +
                '}';
    }
}
