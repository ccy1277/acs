package com.ccy1277.acs.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 菜单传输对象
 * created by ccy on 2022/5/19
 */
@Data
@ApiModel(value="Menu传输对象", description="菜单")
public class MenuDto {
    @NotNull(groups = {update.class})
    @ApiModelProperty(value = "菜单id")
    private Long id;

    @ApiModelProperty(value = "父级ID")
    private Long parentId;

    @NotEmpty(groups = {save.class})
    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单前端图标")
    private String icon;

    @ApiModelProperty(value = "控制前端是否显示 1:显示 0: 隐藏")
    private Integer hidden;

    public interface update{}
    public interface save{}
}
