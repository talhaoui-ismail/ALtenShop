package com.alten.shop.enums;

import java.util.Arrays;
import java.util.Optional;

public enum InventoryStatusEnum {
    INSTOCK(1,"INSTOCK"), LOWSTOCK(2,"LOWSTOCK"), OUTOFSTOCK(3,"OUTOFSTOCK");
    private final String label;
    private final int code ;
    InventoryStatusEnum(int code , String label){
            this .code=code;
            this.label=label;
    }
    public static String findLabelByCode(int code){
        Optional< InventoryStatusEnum> inventoryStatusEnum= Arrays.stream(InventoryStatusEnum.values()).filter(status->status.code==code).findFirst();
        return inventoryStatusEnum.map(statusEnum -> statusEnum.label).orElse(null);
    }
}