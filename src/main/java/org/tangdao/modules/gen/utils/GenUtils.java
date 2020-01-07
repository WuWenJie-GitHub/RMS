package org.tangdao.modules.gen.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.tangdao.common.beetl.BeetlUtils;
import org.tangdao.common.collect.ListUtils;
import org.tangdao.common.collect.MapUtils;
import org.tangdao.common.io.FileUtils;
import org.tangdao.common.lang.DateUtils;
import org.tangdao.common.lang.StringUtils;
import org.tangdao.common.mapper.JaxbMapper;
import org.tangdao.modules.gen.model.domain.GenTable;
import org.tangdao.modules.gen.model.domain.GenTableColumn;
import org.tangdao.modules.gen.model.vo.GenCategory;
import org.tangdao.modules.gen.model.vo.GenConfig;
import org.tangdao.modules.gen.model.vo.GenTemplate;
import org.tangdao.modules.sys.utils.UserUtils;

public class GenUtils {

	private static Logger logger = LoggerFactory.getLogger(GenUtils.class);
	
	private static String jdbcType = "mysql";

	/**
	 * 初始化列属性字段
	 * @param genTable
	 */
	public static void initColumnField(GenTable genTable){
		for (GenTableColumn column : genTable.getColumnList()){
			
			// 如果是不是新增列，则跳过。
			if (StringUtils.isNotBlank(column.getId())){
				continue;
			}
			Map<String, Object> optionMap = MapUtils.newHashMap();
			// 设置字段说明
			if (StringUtils.isBlank(column.getComments())){
				column.setComments(column.getColumnName());
			}
			
			// 设置java类型
			if (StringUtils.startsWithIgnoreCase(column.getJdbcType(), "CHAR") || StringUtils.startsWithIgnoreCase(column.getJdbcType(), "VARCHAR") || StringUtils.startsWithIgnoreCase(column.getJdbcType(), "NARCHAR")){
				column.setJavaType("String");
			}else if (StringUtils.startsWithIgnoreCase(column.getJdbcType(), "DATETIME") || StringUtils.startsWithIgnoreCase(column.getJdbcType(), "DATE") || StringUtils.startsWithIgnoreCase(column.getJdbcType(), "TIMESTAMP")){
				column.setJavaType("java.util.Date");
				column.setShowType("dateselect");
			}else if (StringUtils.startsWithIgnoreCase(column.getJdbcType(), "BIGINT") || StringUtils.startsWithIgnoreCase(column.getJdbcType(), "NUMBER") || StringUtils.startsWithIgnoreCase(column.getJdbcType(), "decimal")){
				// 如果是浮点型
				String[] ss = StringUtils.split(StringUtils.substringBetween(column.getJdbcType(), "(", ")"), ",");
				if (ss != null && ss.length == 2 && Integer.parseInt(ss[1])>0){
					column.setJavaType("Double");
				} else if (ss != null && ((ss.length == 1 && Integer.parseInt(ss[0])<=10)||ss.length == 2 && Integer.parseInt(ss[1])==0)){// 如果是整形
					column.setJavaType("Integer");
				} else{// 长整形
					column.setJavaType("Long");
				}
			}
			
			// 设置java字段名
			column.setJavaField(StringUtils.camelCase(column.getColumnName()));
			
			// 是否是主键
			genTable.getPkList().stream().forEach((item)->{
				if(column.getColumnName().equalsIgnoreCase(item.getColumnName())) {
					column.setIsPk("1");
					column.setIsEdit("1");
				}else {
					column.setIsPk("0");
				}
			});

			// 插入字段
			column.setIsInsert("1");
			
			// 更新
			if (!column.getIsPk().equals("1")
					&& !StringUtils.equalsIgnoreCase(column.getColumnName(), "create_by")
					&& !StringUtils.equalsIgnoreCase(column.getColumnName(), "create_time")
					&& !StringUtils.equalsIgnoreCase(column.getColumnName(), "status")){
				column.setIsUpdate("1");
			}
			
			if (StringUtils.equalsIgnoreCase(column.getColumnName(), "tree_sort")
					||StringUtils.equalsIgnoreCase(column.getColumnName(), "remarks")){
				column.setIsEdit("1");
			}
			
			// 编辑字段（控制页面）
			if (!column.getIsPk().equals("1")
					&& !StringUtils.equalsIgnoreCase(column.getColumnName(), "remarks")
					&& !StringUtils.equalsIgnoreCase(column.getColumnName(), "create_time")
					&& !StringUtils.equalsIgnoreCase(column.getColumnName(), "create_by")
					&& !StringUtils.equalsIgnoreCase(column.getColumnName(), "update_time")
					&& !StringUtils.equalsIgnoreCase(column.getColumnName(), "update_by")
					&& !StringUtils.equalsIgnoreCase(column.getColumnName(), "parent_code")
					&& !StringUtils.equalsIgnoreCase(column.getColumnName(), "parent_codes")
					&& !StringUtils.equalsIgnoreCase(column.getColumnName(), "tree_names")
					&& !StringUtils.equalsIgnoreCase(column.getColumnName(), "tree_sort")
					&& !StringUtils.equalsIgnoreCase(column.getColumnName(), "tree_leaf")
					&& !StringUtils.equalsIgnoreCase(column.getColumnName(), "tree_level")
					&& !StringUtils.equalsIgnoreCase(column.getColumnName(), "status")){
				
				column.setIsList("1");
				column.setIsEdit("1");
			}

			// 列表字段
			if (StringUtils.equalsIgnoreCase(column.getColumnName(), "name")
					|| StringUtils.equalsIgnoreCase(column.getColumnName(), "tree_sort")
					|| StringUtils.equalsIgnoreCase(column.getColumnName(), "title")
					|| StringUtils.equalsIgnoreCase(column.getColumnName(), "status")
					|| StringUtils.equalsIgnoreCase(column.getColumnName(), "update_time")){
				column.setIsList("1");
			}
			
			// 查询字段
			if (StringUtils.equalsIgnoreCase(column.getColumnName(), "name")
					|| StringUtils.equalsIgnoreCase(column.getColumnName(), "title")
					|| StringUtils.equalsIgnoreCase(column.getColumnName(), "parent_code")
					|| StringUtils.equalsIgnoreCase(column.getColumnName(), "parent_codes")
					|| StringUtils.equalsIgnoreCase(column.getColumnName(), "tree_names")
					|| StringUtils.equalsIgnoreCase(column.getColumnName(), "status")){
				column.setIsQuery("1");
			}
			
			// 查询字段类型
			if (StringUtils.equalsIgnoreCase(column.getColumnName(), "name")
					|| StringUtils.equalsIgnoreCase(column.getColumnName(), "title")){
				column.setQueryType("like");
			}
			
			// 
			if (StringUtils.equalsIgnoreCase(column.getColumnName(), "tree_sort")
					|| StringUtils.equalsIgnoreCase(column.getColumnName(), "tree_level")){
				optionMap.put("fieldValid", "digits");
				column.setOptionMap(optionMap);
			}

			// 设置特定类型和字段名
			
			// 用户
//			if (StringUtils.startsWithIgnoreCase(column.getColumnName(), "user_id")){
//				column.setJavaType(User.class.getName());
//				column.setJavaField(column.getJavaField().replaceAll("Id", ".id|name"));
//				column.setShowType("userselect");
//			}
//			// 部门
//			else if (StringUtils.startsWithIgnoreCase(column.getName(), "office_id")){
//				column.setJavaType(Office.class.getName());
//				column.setJavaField(column.getJavaField().replaceAll("Id", ".id|name"));
//				column.setShowType("officeselect");
//			}
//			// 区域
//			else if (StringUtils.startsWithIgnoreCase(column.getName(), "area_id")){
//				column.setJavaType(Area.class.getName());
//				column.setJavaField(column.getJavaField().replaceAll("Id", ".id|name"));
//				column.setShowType("areaselect");
//			}
//			// 创建者、更新者
//			else if (StringUtils.startsWithIgnoreCase(column.getColumnName(), "create_by")
//					|| StringUtils.startsWithIgnoreCase(column.getColumnName(), "update_by")){
//				column.setJavaType(User.class.getName());
//				column.setJavaField(column.getJavaField() + ".id");
//			}
			// 创建时间、更新时间
			else if (StringUtils.startsWithIgnoreCase(column.getColumnName(), "create_time")
					|| StringUtils.startsWithIgnoreCase(column.getColumnName(), "update_time")){
				column.setShowType("datetime");
			}
			// 备注、内容
			else if (StringUtils.equalsIgnoreCase(column.getColumnName(), "remarks")
					|| StringUtils.equalsIgnoreCase(column.getColumnName(), "content")){
				column.setShowType("textarea");
				optionMap.put("gridRowCol", "12/2/10");
				optionMap.put("isNewLine", 1);
				column.setOptionMap(optionMap);
			}
			// 父级ID
			else if (StringUtils.equalsIgnoreCase(column.getColumnName(), "parent_code")){
				column.setJavaType("This");
				column.setJavaField("parentCode");
				column.setShowType("treeselect");
			}
			// 所有父级ID
			else if (StringUtils.equalsIgnoreCase(column.getColumnName(), "parent_codes")){
				column.setQueryType("like");
			}
			// 删除标记
			else if (StringUtils.equalsIgnoreCase(column.getColumnName(), "status")){
				column.setShowType("select");
				
				optionMap.put("dictType", "sys_status");
				optionMap.put("dictName", "sys_status");
				column.setOptionMap(optionMap);
			}
			if (StringUtils.equalsIgnoreCase(column.getIsNull(), "1")){
				column.setIsNull("0");
			}else {
				column.setIsNull("1");
			}
			
		}
	}
	
	/**
	 * 获取模板路径
	 * @return
	 */
	public static String getTemplatePath(){
		try{
			File file = new DefaultResourceLoader().getResource("").getFile();
			if(file != null){
				return file.getAbsolutePath() + File.separator + StringUtils.replaceEach(GenUtils.class.getName(),  new String[]{"util."+GenUtils.class.getSimpleName(), "."}, new String[]{"template", File.separator});
			}			
		}catch(Exception e){
			logger.error("{}", e);
		}

		return "";
	}
	
	/**
	 * XML文件转换为对象
	 * @param fileName
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fileToObject(String fileName, Class<?> clazz){
		try {
			String pathName = "/templates/modules/gen/code/" + fileName;
//			logger.debug("File to object: {}", pathName);
			Resource resource = new ClassPathResource(pathName); 
			InputStream is = resource.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuilder sb = new StringBuilder();  
			while (true) {
				String line = br.readLine();
				if (line == null){
					break;
				}
				sb.append(line).append("\r\n");
			}
			if (is != null) {
				is.close();
			}
			if (br != null) {
				br.close();
			}
//			logger.debug("Read file content: {}", sb.toString());
			return (T) JaxbMapper.fromXml(sb.toString(), clazz);
		} catch (IOException e) {
			logger.warn("Error file convert: {}", e.getMessage());
		}
//		String pathName = StringUtils.replace(getTemplatePath() + "/" + fileName, "/", File.separator);
//		logger.debug("file to object: {}", pathName);
//		String content = "";
//		try {
//			content = FileUtils.readFileToString(new File(pathName), "utf-8");
////			logger.debug("read config content: {}", content);
//			return (T) JaxbMapper.fromXml(content, clazz);
//		} catch (IOException e) {
//			logger.warn("error convert: {}", e.getMessage());
//		}
		return null;
	}
	
	/**
	 * 获取代码生成配置对象
	 * @return
	 */
	public static GenConfig getConfig(){
		return fileToObject("config.xml", GenConfig.class);
	}

	/**
	 * 根据分类获取模板列表
	 * @param config
	 * @param genScheme
	 * @param isChildTable 是否是子表
	 * @return
	 */
	public static List<GenTemplate> getTemplateList(GenConfig config, String category, boolean isChildTable){
		List<GenTemplate> templateList = ListUtils.newArrayList();
		if (config !=null && config.getTplCategoryList() != null && category !=  null){
			for (GenCategory e : config.getTplCategoryList()){
				if (category.equals(e.getValue())){
					List<String> list = null;
					if (!isChildTable){
						list = e.getTemplate();
					}else{
						list = e.getChildTableTemplate();
					}
					if (list != null){
						for (String s : list){
							if (StringUtils.startsWith(s, GenCategory.CATEGORY_REF)){
								templateList.addAll(getTemplateList(config, StringUtils.replace(s, GenCategory.CATEGORY_REF, ""), false));
							}else{
								GenTemplate template = fileToObject(s, GenTemplate.class);
								if (template != null){
									templateList.add(template);
								}
							}
						}
					}
					break;
				}
			}
		}
		return templateList;
	}
	
	/**
	 * 获取数据模型
	 * @param genScheme
	 * @param genTable
	 * @return
	 */
	public static Map<String, Object> getDataModel(GenTable genTable){
		Map<String, Object> model = MapUtils.newHashMap();
		
		model.put("packageName", StringUtils.lowerCase(genTable.getPackageName()));
		model.put("lastPackageName", StringUtils.substringAfterLast((String)model.get("packageName"),"."));
		model.put("moduleName", StringUtils.lowerCase(genTable.getModuleName()));
		model.put("subModuleName", StringUtils.lowerCase(genTable.getSubModuleName()));
		model.put("className", StringUtils.uncapitalize(genTable.getClassName()));
		model.put("ClassName", StringUtils.capitalize(genTable.getClassName()));
		
		model.put("functionName", genTable.getFunctionName());
		model.put("functionNameSimple", genTable.getFunctionNameSimple());
		model.put("functionAuthor", StringUtils.isNotBlank(genTable.getFunctionAuthor())?genTable.getFunctionAuthor():UserUtils.getUser().getUsername());
		model.put("functionVersion", DateUtils.getDate());
		
		model.put("urlPrefix", model.get("moduleName")+(StringUtils.isNotBlank(genTable.getSubModuleName()) ?"/"+StringUtils.lowerCase(genTable.getSubModuleName()):"")+"/"+model.get("className"));
		model.put("viewPrefix", model.get("urlPrefix"));
		model.put("permissionPrefix", model.get("moduleName")+(StringUtils.isNotBlank(genTable.getSubModuleName()) ?":"+StringUtils.lowerCase(genTable.getSubModuleName()):"")+":"+model.get("className"));
		
		model.put("dbType", jdbcType);

		model.put("table", genTable);
		
		return model;
	}
	
	/**
     * 获取工程路径
     * @return
     */
    public static String getProjectPath(String projectPath){
		if (StringUtils.isNotBlank(projectPath)){
			return projectPath;
		}
		try {
			File file = new DefaultResourceLoader().getResource("").getFile();
			if (file != null){
				while(true){
					File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
					if (f == null || f.exists()){
						break;
					}
					if (file.getParentFile() != null){
						file = file.getParentFile();
					}else{
						break;
					}
				}
				projectPath = file.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectPath;
    }
	
	/**
	 * 生成到文件
	 * @param tpl
	 * @param model
	 * @param replaceFile
	 * @return
	 */
	public static String generateToFile(GenTemplate tpl, Map<String, Object> model, GenTable genTable){
		if (StringUtils.isBlank(genTable.getGenBaseDir())) {	
            genTable.setGenBaseDir(FileUtils.getProjectPath());	
        }	
		
		// 获取生成文件
		String fileName = genTable.getGenBaseDir() 
				+ File.separator 
				+ StringUtils.replaceEach(BeetlUtils.renderFromString(tpl.getFilePath() + "/", model), new String[]{"//", "/", "."}, new String[]{File.separator, File.separator, File.separator}) 
				+ BeetlUtils.renderFromString(tpl.getFileName(), model);
		logger.debug(" fileName === " + fileName);

		// 获取生成文件内容
		String content = BeetlUtils.renderFromString(StringUtils.trimToEmpty(tpl.getContent()), model);
		logger.debug(" content === \r\n" + content);
		
		
		if("2".equals(genTable.getFlag())) {
			
			// 如果选择替换文件，则删除原文件
			if (genTable.getReplaceFile()){
				FileUtils.deleteFile(fileName);
			}
			// 创建并写入文件
			if (FileUtils.createFile(fileName)){
				FileUtils.writeToFile(fileName, content, true);
				logger.debug(" file create === " + fileName);
				return "生成成功："+fileName+"<br/>";
			}
			logger.debug(" file extents === " + fileName);
			return "文件已存在："+fileName+"<br/>";
		}
		return fileName+"<br/>";
	}
//	
//	public static void main(String[] args) {
//		try {
//			GenConfig config = getConfig();
//			System.out.println(config);
//			System.out.println(JaxbMapper.toXml(config));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
