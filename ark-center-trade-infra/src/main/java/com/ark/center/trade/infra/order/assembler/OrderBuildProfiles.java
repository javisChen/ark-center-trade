package com.ark.center.trade.infra.order.assembler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderBuildProfiles {

    private Boolean withOrderItems;
    private Boolean withReceive;

    @Override
    public String toString() {
        return "OrderBuildProfiles{" +
                "withOrderItems=" + withOrderItems +
                ", withReceive=" + withReceive +
                '}';
    }
}
