package org.tangdao.modules.sys.utils;

import java.util.List;

import org.tangdao.common.collect.ListUtils;
import org.tangdao.common.lang.StringUtils;
import org.tangdao.common.mapper.JsonMapper;
import org.tangdao.common.utils.SpringUtils;
import org.tangdao.modules.sys.model.domain.DictData;
import org.tangdao.modules.sys.service.IDictDataService;

public class DictUtils {

	private static final class Static {
		private static IDictDataService dictDataService = SpringUtils.getBean(IDictDataService.class);
	}

	public static String getDictLabels(String dictType, String dictValues, String defaultValue) {
		if (StringUtils.isNotBlank(dictType) && StringUtils.isNotBlank(dictValues)) {
			List<String> targetList = ListUtils.newArrayList();
			String[] arr = dictValues.split(",");
			for (String str : arr) {
				if (StringUtils.isNotBlank(str)) {
					targetList.add(getDictLabel(dictType, str, defaultValue));
				}
			}
			return StringUtils.join(targetList, ",");
		}
		return defaultValue;
	}

	public static String getDictValues(String dictType, String dictLabels, String defaultValue) {
		if (StringUtils.isNotBlank(dictType) && StringUtils.isNotBlank(dictLabels)) {
			List<String> targetList = ListUtils.newArrayList();
			String[] arr = dictLabels.split(",");
			for (String str : arr) {
				if (StringUtils.isNotBlank(str)) {
					targetList.add(getDictValue(dictType, str, defaultValue));
				}
			}
			return StringUtils.join(targetList, ",");
		}
		return defaultValue;
	}

	public static String getDictLabel(String dictType, String dictValue, String defaultValue) {
		if (StringUtils.isNotBlank(dictType) && StringUtils.isNotBlank(dictValue)) {
			List<DictData> list = getDictList(dictType);
			if (list != null) {
				for (DictData map : list) {
					if(dictValue.equals(map.getDictValue())) {
						return map.getDictLabel();
					}
				}
			}
		}
		return defaultValue;
	}

	public static String getDictValue(String dictType, String dictLabel, String defaultValue) {
		if (StringUtils.isNotBlank(dictType) && StringUtils.isNotBlank(dictLabel)) {
			List<DictData> list = getDictList(dictType);
			if (list != null) {
				for (DictData map : list) {
					if(dictLabel.equals(map.getDictLabel())) {
						return map.getDictValue();
					}
				}
			}
		}
		return defaultValue;
	}

	public static List<DictData> getDictList(String dictType) {
		return Static.dictDataService.getDictDataList(dictType);
	}

	public static String getDictListJson(String dictType) {
		try {
			return JsonMapper.toJson(getDictList(dictType));
		} catch (Exception e) {
			return "[]";
		}
	}
}
