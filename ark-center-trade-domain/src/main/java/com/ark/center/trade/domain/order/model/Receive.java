package com.ark.center.trade.domain.order.model;

import lombok.Data;

@Data
public class Receive {

    /**
     * 收货人名称
     */
    private String name;

    /**
     * 收货人电话
     */
    private String mobile;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 街道
     */
    private String street;

    /**
     * 详细地址
     */
    private String address;

}