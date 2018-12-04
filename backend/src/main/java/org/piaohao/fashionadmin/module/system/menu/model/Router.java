package org.piaohao.fashionadmin.module.system.menu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Router {
    private String path;
    private String name;
    private String icon;
    private String component;
    private List<Router> routes;
    private String code;
    private String parentCode;
}
