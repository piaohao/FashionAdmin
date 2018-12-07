package org.piaohao.fashionadmin.db.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.piaohao.fashionadmin.util.UserSerializer;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author piaohao
 * @since 2018-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userName;
    @JsonSerialize(using = UserSerializer.class)
    private String password;

    private String nickName;
    @JsonIgnore
    private Integer status;
    @JsonIgnore
    private Long createdTime;
    @JsonIgnore
    private Long updateTime;


}
