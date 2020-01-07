package org.tangdao.modules.sys.model.domain;

import javax.validation.constraints.NotBlank;

import org.tangdao.common.suports.DataEntity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 参数配置表
 * </p>
 *
 * @author ruyang
 * @since 2019-07-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_config")
public class Config extends DataEntity<Config>{

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId
    private String id;

    /**
     * 名称
     */
    @NotBlank(message="参数名称不能为空")
    private String configName;

    /**
     * 参数键
     */
    @NotBlank(message="参数键名不能为空")
    private String configKey;

    /**
     * 参数值
     */
    @NotBlank(message="参数键值不能为空")
    private String configValue;
    
}
