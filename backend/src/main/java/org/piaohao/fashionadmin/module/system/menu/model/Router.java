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
public class Router {
    private String path;
    private String name;
    private String icon;
    private String component;
    private List<Router> routes;
    private String redirect;
    @JsonIgnore
    private String code;
    @JsonIgnore
    private String parentCode;
}
