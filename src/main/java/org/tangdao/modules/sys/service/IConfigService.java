package org.tangdao.modules.sys.service;

import org.tangdao.common.service.ICrudService;
import org.tangdao.modules.sys.model.domain.Config;

/**
 * <p>
 * 参数配置表 服务类
 * </p>
 *
 * @author ruyang
 * @since 2019-07-02
 */
public interface IConfigService extends ICrudService<Config> {
	
	public Config getConfigByKey(String configKey);

	public boolean reloadToRedis();
}
