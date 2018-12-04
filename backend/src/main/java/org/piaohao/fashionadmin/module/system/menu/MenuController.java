package org.piaohao.fashionadmin.module.system.menu;

import cn.hutool.json.JSONUtil;
import org.piaohao.fashionadmin.annotation.ClearAuth;
import org.piaohao.fashionadmin.db.system.entity.Permission;
import org.piaohao.fashionadmin.db.system.service.IPermissionService;
import org.piaohao.fashionadmin.module.system.menu.model.Menu;
import org.piaohao.fashionadmin.module.system.menu.model.Router;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MenuController {
    @Autowired
    private IPermissionService permissionService;

    @GetMapping("/api/getMenu")
    public Object getMenu2() {
        String jsonStr = "[\n" +
                "    {\n" +
                "        \"path\": \"/dashboard\",\n" +
                "        \"name\": \"Dashboard\",\n" +
                "        \"icon\": \"dashboard\",\n" +
                "        \"locale\": \"menu.dashboard\",\n" +
                "        \"children\": [\n" +
                "            {\n" +
                "                \"path\": \"/dashboard/analysis\",\n" +
                "                \"name\": \"分析页\",\n" +
                "                \"exact\": true,\n" +
                "                \"locale\": \"menu.dashboard.analysis\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"path\": \"/dashboard/monitor\",\n" +
                "                \"name\": \"监控页\",\n" +
                "                \"exact\": true,\n" +
                "                \"locale\": \"menu.dashboard.monitor\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"path\": \"/dashboard/workplace\",\n" +
                "                \"name\": \"工作台\",\n" +
                "                \"exact\": true,\n" +
                "                \"locale\": \"menu.dashboard.workplace\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"path\": \"/form\",\n" +
                "        \"icon\": \"form\",\n" +
                "        \"name\": \"表单页\",\n" +
                "        \"locale\": \"menu.form\",\n" +
                "        \"children\": [\n" +
                "            {\n" +
                "                \"path\": \"/form/basic-form\",\n" +
                "                \"name\": \"基础表单\",\n" +
                "                \"exact\": true,\n" +
                "                \"locale\": \"menu.form.basicform\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"path\": \"/form/step-form\",\n" +
                "                \"name\": \"分步表单\",\n" +
                "                \"hideChildrenInMenu\": true,\n" +
                "                \"locale\": \"menu.form.stepform\",\n" +
                "                \"children\": [\n" +
                "                    {\n" +
                "                        \"path\": \"/form/step-form/info\",\n" +
                "                        \"name\": \"分步表单（填写转账信息）\",\n" +
                "                        \"exact\": true,\n" +
                "                        \"locale\": \"menu.form.stepform.info\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"path\": \"/form/step-form/confirm\",\n" +
                "                        \"name\": \"分步表单（确认转账信息）\",\n" +
                "                        \"exact\": true,\n" +
                "                        \"locale\": \"menu.form.stepform.confirm\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"path\": \"/form/step-form/result\",\n" +
                "                        \"name\": \"分步表单（完成）\",\n" +
                "                        \"exact\": true,\n" +
                "                        \"locale\": \"menu.form.stepform.result\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"path\": \"/form/advanced-form\",\n" +
                "                \"name\": \"高级表单\",\n" +
                "                \"authority\": [\"admin\"],\n" +
                "                \"exact\": true,\n" +
                "                \"locale\": \"menu.form.advancedform\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"path\": \"/list\",\n" +
                "        \"icon\": \"table\",\n" +
                "        \"name\": \"列表页\",\n" +
                "        \"authority\": [\"user\"],\n" +
                "        \"locale\": \"menu.list\",\n" +
                "        \"children\": [\n" +
                "            {\n" +
                "                \"path\": \"/list/table-list\",\n" +
                "                \"name\": \"查询表格\",\n" +
                "                \"exact\": true,\n" +
                "                \"locale\": \"menu.list.searchtable\",\n" +
                "                \"authority\": [\"user\"]\n" +
                "            },\n" +
                "            {\n" +
                "                \"path\": \"/list/basic-list\",\n" +
                "                \"name\": \"标准列表\",\n" +
                "                \"exact\": true,\n" +
                "                \"locale\": \"menu.list.basiclist\",\n" +
                "                \"authority\": [\"user\"]\n" +
                "            },\n" +
                "            {\n" +
                "                \"path\": \"/list/card-list\",\n" +
                "                \"name\": \"卡片列表\",\n" +
                "                \"exact\": true,\n" +
                "                \"locale\": \"menu.list.cardlist\",\n" +
                "                \"authority\": [\"user\"]\n" +
                "            },\n" +
                "            {\n" +
                "                \"path\": \"/list/search\",\n" +
                "                \"name\": \"搜索列表\",\n" +
                "                \"locale\": \"menu.list.searchlist\",\n" +
                "                \"authority\": [\"user\"],\n" +
                "                \"children\": [\n" +
                "                    {\n" +
                "                        \"path\": \"/list/search/articles\",\n" +
                "                        \"name\": \"搜索列表（文章）\",\n" +
                "                        \"exact\": true,\n" +
                "                        \"locale\": \"menu.list.searchlist.articles\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"path\": \"/list/search/projects\",\n" +
                "                        \"name\": \"搜索列表（项目）\",\n" +
                "                        \"exact\": true,\n" +
                "                        \"locale\": \"menu.list.searchlist.projects\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"path\": \"/list/search/applications\",\n" +
                "                        \"name\": \"搜索列表（应用）\",\n" +
                "                        \"exact\": true,\n" +
                "                        \"locale\": \"menu.list.searchlist.applications\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"path\": \"/profile\",\n" +
                "        \"name\": \"详情页\",\n" +
                "        \"icon\": \"profile\",\n" +
                "        \"locale\": \"menu.profile\",\n" +
                "        \"children\": [\n" +
                "            {\n" +
                "                \"path\": \"/profile/basic\",\n" +
                "                \"name\": \"基础详情页\",\n" +
                "                \"exact\": true,\n" +
                "                \"locale\": \"menu.profile.basic\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"path\": \"/profile/advanced\",\n" +
                "                \"name\": \"高级详情页\",\n" +
                "                \"authority\": [\"admin\"],\n" +
                "                \"exact\": true,\n" +
                "                \"locale\": \"menu.profile.advanced\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"结果页\",\n" +
                "        \"icon\": \"check-circle-o\",\n" +
                "        \"path\": \"/result\",\n" +
                "        \"locale\": \"menu.result\",\n" +
                "        \"children\": [\n" +
                "            {\n" +
                "                \"path\": \"/result/success\",\n" +
                "                \"name\": \"成功页\",\n" +
                "                \"exact\": true,\n" +
                "                \"locale\": \"menu.result.success\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"path\": \"/result/fail\",\n" +
                "                \"name\": \"失败页\",\n" +
                "                \"exact\": true,\n" +
                "                \"locale\": \"menu.result.fail\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"异常页\",\n" +
                "        \"icon\": \"warning\",\n" +
                "        \"path\": \"/exception\",\n" +
                "        \"locale\": \"menu.exception\",\n" +
                "        \"children\": [\n" +
                "            {\n" +
                "                \"path\": \"/exception/403\",\n" +
                "                \"name\": \"403\",\n" +
                "                \"exact\": true,\n" +
                "                \"locale\": \"menu.exception.not-permission\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"path\": \"/exception/404\",\n" +
                "                \"name\": \"404\",\n" +
                "                \"exact\": true,\n" +
                "                \"locale\": \"menu.exception.not-find\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"path\": \"/exception/500\",\n" +
                "                \"name\": \"500\",\n" +
                "                \"exact\": true,\n" +
                "                \"locale\": \"menu.exception.server-error\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"path\": \"/exception/trigger\",\n" +
                "                \"name\": \"触发错误\",\n" +
                "                \"hideInMenu\": true,\n" +
                "                \"exact\": true,\n" +
                "                \"locale\": \"menu.exception.trigger\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"个人页\",\n" +
                "        \"icon\": \"user\",\n" +
                "        \"path\": \"/account\",\n" +
                "        \"locale\": \"menu.account\",\n" +
                "        \"children\": [\n" +
                "            {\n" +
                "                \"path\": \"/account/center\",\n" +
                "                \"name\": \"个人中心\",\n" +
                "                \"locale\": \"menu.account.center\",\n" +
                "                \"children\": []\n" +
                "            },\n" +
                "            {\n" +
                "                \"path\": \"/account/settings\",\n" +
                "                \"name\": \"个人设置\",\n" +
                "                \"locale\": \"menu.account.settings\",\n" +
                "                \"children\": []\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "  {\n" +
                "    \"name\": \"测试页\",\n" +
                "    \"icon\": \"tool\",\n" +
                "    \"path\": \"/test\",\n" +
                "    \"locale\": \"menu.test\",\n" +
                "    \"children\": [\n" +
                "      {\n" +
                "        \"path\": \"/test/index\",\n" +
                "        \"name\": \"测试首页\",\n" +
                "        \"icon\": \"tool\",\n" +
                "        \"locale\": \"menu.test.index\",\n" +
                "        \"children\": []\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]";
        return JSONUtil.parseArray(jsonStr);
    }

    @GetMapping("/api/getMenu1")
    public Object getMenu() {
        List<Permission> permissions = permissionService.list();
        List<Menu> menus = permissions.stream()
                .map(p -> Menu.builder()
                        .name(p.getName())
                        .path(p.getUrl())
                        .icon(p.getIcon())
                        .code(p.getCode())
                        .parentCode(p.getParentCode())
                        .build())
                .collect(Collectors.toList());
        return getMenus("root", menus);
    }

    @ClearAuth
    @GetMapping("/api/allMenu")
    public Object allMenu() {
        List<Permission> permissions = permissionService.list();
        List<Router> routers = permissions.stream()
                .map(p -> Router.builder()
                        .name(p.getName())
                        .path(p.getUrl())
                        .icon(p.getIcon())
                        .code(p.getCode())
                        .parentCode(p.getParentCode())
                        .component(p.getComponent())
                        .build())
                .collect(Collectors.toList());
        return getRoutes("root", routers);
    }

    /**
     * 递归构建菜单
     *
     * @param code  父菜单code
     * @param menus 全量menu
     * @return
     */
    private List<Menu> getMenus(String code, List<Menu> menus) {
        List<Menu> childMenus = menus.stream()
                .filter(m -> m.getParentCode().equals(code))
                .collect(Collectors.toList());
        childMenus.forEach(m -> {
            m.setChildren(getMenus(m.getCode(), menus));
        });
        return childMenus;
    }

    private List<Router> getRoutes(String code, List<Router> routes) {
        List<Router> childRoutes = routes.stream()
                .filter(m -> m.getParentCode().equals(code))
                .collect(Collectors.toList());
        childRoutes.forEach(m -> m.setRoutes(getRoutes(m.getCode(), routes)));
        return childRoutes;
    }

}
