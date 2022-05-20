package com.ccy1277.acs.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 资源分类dto
 * created by ccy on 2022/5/20
 */
@Data
public class ResourceCatDto {
    @NotNull(groups = {update.class})
    @ApiModelProperty(value = "分类id")
    private Long id;

    @NotEmpty(groups = {save.class})
    @ApiModelProperty(value = "分类名")
    private String name;

    @ApiModelProperty(value = "分类描述")
    private String description;

    public interface update{}

    public interface save{}
}
