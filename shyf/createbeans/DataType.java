package com.shyf.createbeans;

public class DataType {
	// 判断字符串是否属于基本数据类型的名字
	public boolean isDataType(String str) {
		if (str.equals(String.class.getSimpleName())) {
			return true;
		}
		if (str.equals(int.class.getSimpleName())) {
			return true;
		}
		if (str.equals(double.class.getSimpleName())) {
			return true;
		}
		if (str.equals(float.class.getSimpleName())) {
			return true;
		}
		if (str.equals(long.class.getSimpleName())) {
			return true;
		}
		if (str.equals(short.class.getSimpleName())) {
			return true;
		}
		if (str.equals(char.class.getSimpleName())) {
			return true;
		}
		if (str.equals(byte.class.getSimpleName())) {
			return true;
		}
		if (str.equals(boolean.class.getSimpleName())) {
			return true;
		}
		if (str.equals(Integer.class.getSimpleName())) {
			return true;
		}
		if (str.equals(Double.class.getSimpleName())) {
			return true;
		}
		if (str.equals(Float.class.getSimpleName())) {
			return true;
		}
		if (str.equals(Character.class.getSimpleName())) {
			return true;
		}
		if (str.equals(Long.class.getSimpleName())) {
			return true;
		}
		if (str.equals(Short.class.getSimpleName())) {
			return true;
		}
		if (str.equals(Byte.class.getSimpleName())) {
			return true;
		}
		if (str.equals(Boolean.class.getSimpleName())) {
			return true;
		}
		return false;
	}
	//将数据库中字段类型转化为对应的java数据类型
	public String dataTypeTranform(String dataType){
		if("Integer".equals(dataType)){
			return "int";
		}else if("String".equals(dataType)){
			return "String";
		}else if("Double".equals(dataType)){
			return "double";
		}else if("Timestamp".equals(dataType)){
			return "long";
		}
		return dataType;
	}
}
