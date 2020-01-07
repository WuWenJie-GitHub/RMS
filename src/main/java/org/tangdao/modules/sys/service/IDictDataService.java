package org.tangdao.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.tangdao.common.service.ICrudService;
import org.tangdao.modules.sys.model.domain.DictData;

/**
 * <p>
 * 字典数据表 服务类
 * </p>
 *
 * @author ruyang
 * @since 2019-07-02
 */
public interface IDictDataService extends ICrudService<DictData> {

	/**
	 * 获取数据字典，分类
	 * 
	 * @return
	 */
	public Map<String, List<DictData>> getDictDataList();

	public List<DictData> getDictDataList(String dictType);

	public boolean reloadToRedis();

	public boolean deleteByType(String dictType);

}
