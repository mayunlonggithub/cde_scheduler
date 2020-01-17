package com.zjcds.cde.scheduler.domain.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author luokp on 2019/3/25.
 */
@JsonDeserialize(using = DbTypeCodeEnum.EngineeringStatusDeserializer.class)
public enum DbTypeCodeEnum implements EnumValue<String, String> {
    oracle("1","oracle"),
    mysql("2","mysql"),
    db2("3","db2"),
    Hive2("6","Hive2");

    DbTypeCodeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private String key;

    private String value;

    @Override
    public String getKey() {
        return key;
    }


    public static String getKey(String key) {
        if(key.equals(DbTypeCodeEnum.oracle.getKey())){
            return DbTypeCodeEnum.oracle.getValue();
        }else if(key.equals(DbTypeCodeEnum.mysql.getKey())){
            return DbTypeCodeEnum.mysql.getValue();
        }else if(key.equals(DbTypeCodeEnum.db2.getKey())){
            return DbTypeCodeEnum.db2.getValue();
        }else if(key.equals(DbTypeCodeEnum.Hive2.getKey())){
            return DbTypeCodeEnum.Hive2.getValue();
        }
        return null;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static class EngineeringStatusConverter extends BaseEnumConverter<DbTypeCodeEnum, String> {
        public EngineeringStatusConverter() {
            super(DbTypeCodeEnum.class);
        }
    }

    public static class EngineeringStatusDeserializer extends EnumValueDeserializer<DbTypeCodeEnum> {
        public EngineeringStatusDeserializer() {
            super(DbTypeCodeEnum.class);
        }
    }
}
