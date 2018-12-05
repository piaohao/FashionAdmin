package org.piaohao.fashionadmin.module.system.menu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Menu {
    private String name;
    private String icon;
    private String path;
    private List<Menu> children;
    @JsonIgnore
    private String code;
    @JsonIgnore
    private String parentCode;
    @JsonIgnore
    private String component;
}
