package com.ark.center.trade.infra.order.assembler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderAssembleProfiles {

    private Boolean withOrderItems;
    private Boolean withReceive;

    @Override
    public String toString() {
        return "OrderAssembleProfiles{" +
                "withOrderItems=" + withOrderItems +
                ", withReceive=" + withReceive +
                '}';
    }
}
