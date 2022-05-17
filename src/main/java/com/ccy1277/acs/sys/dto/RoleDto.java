package com.ccy1277.acs.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 角色dto
 * created by ccy on 2022/5/17
 */
@Data
public class RoleDto {
    @NotEmpty(groups = {update.class})
    @ApiModelProperty(value = "角色id(更新角色时必须包含此参数)")
    private String id;

    @ApiModelProperty(value = "角色名(创建角色时必须包含此参数)")
    @NotEmpty(groups = {save.class})
    private String name;

    @ApiModelProperty(value = "角色描述")
    private String description;

    /**
     * 增加角色校验规则
     */
    public interface save{}

    /**
     * 更新角色校验规则
     */
    public interface update{}
}
