package org.example.config.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ConfigEntity {

    private String baseUrl;
    private List<String> resolutions;

}
