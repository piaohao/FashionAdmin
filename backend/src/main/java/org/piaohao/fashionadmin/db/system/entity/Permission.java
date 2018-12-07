package org.piaohao.fashionadmin.db.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author piaohao
 * @since 2018-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String code;

    private String parentCode;

    private String name;

    private String icon;

    private String url;

    /**
     * 组件路径
     */
    private String component;

    private Integer priority;

    private Integer level;

    private Integer isMenu;

    private String tips;

    private Integer status;

    private Long createdTime;

    private Long updateTime;

    @TableField(exist = false)
    private Long key;
    @TableField(exist = false)
    private List<Permission> children;


}
