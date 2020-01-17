package com.zjcds.cde.scheduler.domain.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author huangyj on 2019/9/14.
 */
@JsonDeserialize(using = UserStatus.UserStatusDeserializer.class)
public enum UserStatus implements EnumValue<String, String> {

    ACTIVE("ACTIVE", "激活"),
    DISABLE("DISABLE", "删除");

    private String key;
    private String value;

    UserStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static class UserStatusConverter extends BaseEnumConverter<UserStatus, String> {
        public UserStatusConverter() {
            super(UserStatus.class);
        }

    }

    public static class UserStatusDeserializer extends EnumValueDeserializer {
        public UserStatusDeserializer() {
            super(UserStatus.class);
        }
    }
}
