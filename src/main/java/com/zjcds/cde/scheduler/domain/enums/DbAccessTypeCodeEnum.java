package com.zjcds.cde.scheduler.domain.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author luokp on 2019/3/25.
 */
@JsonDeserialize(using = DbAccessTypeCodeEnum.EngineeringStatusDeserializer.class)
public enum DbAccessTypeCodeEnum implements EnumValue<String, String> {
    Native("1","Native"),
    ODBC("2","ODBC"),
    OCI("3","OCI"),
    JNDI("4","JNDI");

    DbAccessTypeCodeEnum(String key, String value) {
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
        if(key.equals(DbAccessTypeCodeEnum.Native.getKey())){
            return DbAccessTypeCodeEnum.Native.getValue();
        }else if(key.equals(DbAccessTypeCodeEnum.ODBC.getKey())){
            return DbAccessTypeCodeEnum.ODBC.getValue();
        }else if(key.equals(DbAccessTypeCodeEnum.OCI.getKey())){
            return DbAccessTypeCodeEnum.OCI.getValue();
        }else if(key.equals(DbAccessTypeCodeEnum.JNDI.getKey())){
            return DbAccessTypeCodeEnum.JNDI.getValue();
        }
        return null;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static class EngineeringStatusConverter extends BaseEnumConverter<DbAccessTypeCodeEnum, String> {
        public EngineeringStatusConverter() {
            super(DbAccessTypeCodeEnum.class);
        }
    }

    public static class EngineeringStatusDeserializer extends EnumValueDeserializer<DbAccessTypeCodeEnum> {
        public EngineeringStatusDeserializer() {
            super(DbAccessTypeCodeEnum.class);
        }
    }
}
