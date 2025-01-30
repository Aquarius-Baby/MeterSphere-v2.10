package io.metersphere.api.parse.api.postman;

import lombok.Data;

@Data
public class PostmanKeyValue {
    private String key;
    private String value;
    private String type;

    public PostmanKeyValue() {
    }

    public PostmanKeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
