package org.tangdao.modules.gen.model.domain;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlTransient;

import org.tangdao.common.collect.ListUtils;
import org.tangdao.common.collect.MapUtils;
import org.tangdao.common.config.Global;
import org.tangdao.common.lang.StringUtils;
import org.tangdao.common.mapper.JsonMapper;
import org.tangdao.common.suports.DataEntity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("gen_table_column")
public class GenTableColumn extends DataEntity<GenTableColumn> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@TableId
	private String id;
	
	private String tableName; //所属表
	
	@JsonIgnore
	@TableField(exist = false)
	private GenTable genTable;	// 归属表
	private String columnName; 		// 列名
	private String comments;	// 描述
	private String jdbcType;	// JDBC类型
	private String javaType;	// JAVA类型
	private String javaField;	// JAVA字段名
	private String isPk;		// 是否主键（1：主键）
	private String isNull;		// 是否可为空（1：可为空；0：不为空）
	private String isInsert;	// 是否为插入字段（1：插入字段）
	private String isEdit;		// 是否编辑字段（1：编辑字段）
	private String isList;		// 是否列表字段（1：列表字段）
	private String isQuery;		// 是否查询字段（1：查询字段）
	private String queryType;	// 查询方式（等于、不等于、大于、小于、范围、左LIKE、右LIKE、左右LIKE）
	private String showType;	// 字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择）
	private Integer columnSort;		// 排序（升序）
	private String isUpdate;
	
	private String options;
	
	@SuppressWarnings("rawtypes")
	@TableField(exist = false)
	private Map optionMap;
	
	@SuppressWarnings("rawtypes")
	public void setOptions(String options) {
	      if (StringUtils.isNotBlank(options)) {
	         this.optionMap = (Map)JsonMapper.fromJson(options, Map.class);
	      }

	      this.options = options;
	}
	
	@JsonIgnore
	public String getOptions() {
		if (this.optionMap != null) {
			this.options = JsonMapper.toJson(this.optionMap);
		}
		return this.options;
	}
	
	@SuppressWarnings("rawtypes")
	public Map getOptionMap() {
		if (this.optionMap == null) {
			this.optionMap = MapUtils.newHashMap();
		}
		return this.optionMap;
	}

	
	@JsonIgnore	
	@XmlTransient	
	public Global getGlobal() {	
		return Global.getInstance();	
	}

	public GenTableColumn() {
		super();
	}

	public GenTableColumn(GenTable genTable){
		this.genTable = genTable;
	}

	public GenTable getGenTable() {
		return genTable;
	}

	public void setGenTable(GenTable genTable) {
		this.genTable = genTable;
	}
	
	public String getJdbcType() {
		return StringUtils.lowerCase(jdbcType);
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	/**
	 * 获取列名和说明
	 * @return
	 */
	public String getColumnNameAndComments() {
		return getColumnName() + (comments == null ? "" : "  :  " + comments);
	}
	
	/**
	 * 获取字符串长度
	 * @return
	 */
	public String getDataLength(){
		String[] ss = StringUtils.split(StringUtils.substringBetween(getJdbcType(), "(", ")"), ",");
		if (ss != null && ss.length == 1){// && "String".equals(getJavaType())){
			return ss[0];
		}
		return "0";
	}

	/**
	 * 获取简写Java类型
	 * @return
	 */
	public String getSimpleJavaType(){
		if ("This".equals(getJavaType())&&genTable!=null){
			return StringUtils.capitalize(genTable.getClassName());
		}
		return StringUtils.indexOf(getJavaType(), ".") != -1 
				? StringUtils.substringAfterLast(getJavaType(), ".")
						: getJavaType();
	}
	
	/**
	 * 获取简写Java字段
	 * @return
	 */
	public String getSimpleJavaField(){
		return StringUtils.substringBefore(getJavaField(), ".");
	}
	
	/**
	 * 获取Java字段，如果是对象，则获取对象.附加属性1
	 * @return
	 */
	public String getJavaFieldId(){
		return StringUtils.substringBefore(getJavaField(), "|");
	}
	
	/**
	 * 获取Java字段，如果是对象，则获取对象.附加属性2
	 * @return
	 */
	public String getJavaFieldName(){
		String[][] ss = getJavaFieldAttrs();
		return ss.length>0 ? getSimpleJavaField()+"."+ss[0][0] : "";
	}
	
	/**
	 * 获取Java字段，所有属性名
	 * @return
	 */
	public String[][] getJavaFieldAttrs(){
		String[] ss = StringUtils.split(StringUtils.substringAfter(getJavaField(), "|"), "|");
		String[][] sss = new String[ss.length][2];
		if (ss!=null){
			for (int i=0; i<ss.length; i++){
				sss[i][0] = ss[i];
				sss[i][1] = StringUtils.capCamelCase(ss[i]);
			}
		}
		return sss;
	}
	
	/**
	 * 获取列注解列表
	 * @return
	 */
	@JsonIgnore
	public List<String> getAnnotationList(){
		List<String> list = ListUtils.newArrayList();
		// 导入Jackson注解
		if ("This".equals(getJavaType())){
			list.add("com.fasterxml.jackson.annotation.JsonBackReference");
		}
		if ("java.util.Date".equals(getJavaType())){
			list.add("com.fasterxml.jackson.annotation.JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")");
		}
		
		if (!"1".equals(this.getIsPk()) && !"hidden".equals(this.getShowType())) {	
			// 导入JSR303验证依赖包
			if ("1".equals(getIsNull())){
				
				if ("String".equals(getJavaType())){
					list.add("javax.validation.constraints.NotBlank(message=\""+getComments()+"不能为空\")");
				} else {
					list.add("javax.validation.constraints.NotNull(message=\""+getComments()+"不能为空\")");
				}
			}
			if ("String".equals(getJavaType()) && !"0".equals(getDataLength()) && !"-1".equals(this.getDataLength())){
				list.add("org.hibernate.validator.constraints.Length(min=0, max="+getDataLength()
						+", message=\""+getComments()+"长度不能超过  "+getDataLength()+" 个字符\")");
			}
		}
		
		return list;
	}
	
	/**
	 * 获取简写列注解列表
	 * @return
	 */
	@JsonIgnore
	public List<String> getSimpleAnnotationList(){
		List<String> list = ListUtils.newArrayList();
		for (String ann : getAnnotationList()){
			list.add(StringUtils.substringAfterLast(ann, "."));
		}
		return list;
	}
	
	/**
	 * 是否是基类字段
	 * @return
	 */
	@JsonIgnore
	public Boolean getIsNotBaseField(){
		return !StringUtils.equals(getSimpleJavaField(), "remarks")
				&& !StringUtils.equals(getSimpleJavaField(), "createBy")
				&& !StringUtils.equals(getSimpleJavaField(), "createTime")
				&& !StringUtils.equals(getSimpleJavaField(), "updateBy")
				&& !StringUtils.equals(getSimpleJavaField(), "updateTime")
				&& !StringUtils.equals(getSimpleJavaField(), "status")
				&& !StringUtils.equals(getSimpleJavaField(), "treeSort")
				&& !StringUtils.equals(getSimpleJavaField(), "treeNames")
				&& !StringUtils.equals(getSimpleJavaField(), "treeLeaf")
				&& !StringUtils.equals(getSimpleJavaField(), "treeLevel")
				&& !StringUtils.equals(getSimpleJavaField(), "parent")
				&& !StringUtils.equals(getSimpleJavaField(), "parentCode")
				&& !StringUtils.equals(getSimpleJavaField(), "parentCodes")
				;
	}


}
