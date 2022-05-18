package com.ccy1277.acs.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * 资源dto
 * created by ccy on 2022/5/18
 */
@Data
public class ResourceDto {

    @NotEmpty(groups = {update.class})
    @ApiModelProperty(value = "资源id(更新资源时必须包含此参数)")
    private Long id;

    @ApiModelProperty(value = "分类ID")
    private Long categoryId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @NotEmpty(groups = {save.class})
    @ApiModelProperty(value = "资源名称")
    private String name;

    @NotEmpty(groups = {save.class})
    @ApiModelProperty(value = "资源URL")
    private String url;

    @ApiModelProperty(value = "描述")
    private String description;

    public interface update{}
    public interface save{}
}
