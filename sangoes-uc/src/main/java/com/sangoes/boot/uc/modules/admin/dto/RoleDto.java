package com.sangoes.boot.uc.modules.admin.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * RoleDto 角色
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "角色对象", description = "角色对象")
public class RoleDto {
    /**
     * AddRoleGroup
     */
    public interface AddRoleGroup {

    }

    @NotBlank(message = "角色名称不能为空", groups = { AddRoleGroup.class })
    @Length(min = 2, max = 10, message = "角色名称2-10位", groups = { AddRoleGroup.class })
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @NotBlank(message = "角色编码不能为空", groups = { AddRoleGroup.class })
    @Length(min = 2, max = 18, message = "角色编码2-18位", groups = { AddRoleGroup.class })
    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "角色描述")
    private String des;

    @ApiModelProperty(value = "1启用 0禁用")
    private Integer status;
}