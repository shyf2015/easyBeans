package com.shyf.createbeans;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.shyf.util.JDBCutil;

public class CreateBeans {
	private DataType dt = new DataType();
	private String packageName;
	private String daoPackageName;
	//根据所传参数生成对应实体类
	public void createBeans(String className,Map<String,String> maps){
		Properties read = new Properties();
		try {
			read.load(CreateBeans.class.getResourceAsStream("/shyf.properties"));
			//读取配置文件中的包名
			packageName = read.getProperty("beansPackageName");
			String[] dirs = packageName.split("\\.");
			String dirName="";
			for (String string : dirs) {
				dirName += "/"+string;
			}
			//创建实体类包
			File beansPackage = new File("src"+dirName);
			beansPackage.mkdirs();
			//创建实体类
			File beans = new File("src"+dirName+"/"+upFirst(className)+".java");
			beans.createNewFile();
			
			//拼接实体类的内容
			String beansContent = "package "+packageName+";\n";
			//因为数据库中经常会使用到date类型，为了防止出现少导包的情况，默认导入import java.util.Date包，这里有些不合理应该做一步判断，如果存在date的字段类型再去导入
			beansContent += "import java.util.Date;\n";
			//拼接类名
			beansContent += "public class "+upFirst(className)+"{\n";
			//将字段存放到set中
			Set<String> fields = maps.keySet();
			//拼接字段
			for (String field : fields) {
				beansContent += "    private "+maps.get(field)+" "+field+";\n";
			}
			//拼接get方法
			for (String field : fields) {
				beansContent += "\tpublic "+maps.get(field)+" get"+upFirst(field)+"(){\n\t\t"+"return "+field+";\n    }\n";
			}
			//拼接set方法
			for (String field : fields) {
				beansContent += "\tpublic void set"+upFirst(field)+"("+maps.get(field)+" "+field+"){\n\t\t"+"this."+field+" = "+field+";\n    }\n";
			}
			//拼接无参构造方法
			beansContent += "\tpublic "+upFirst(className)+"(){\n\t\t super();\n    }\n";
			//拼接有参构造方法
			beansContent += "\tpublic "+upFirst(className)+"(";
			for (String field : fields) {
				beansContent += maps.get(field)+" "+field+",";
			}
			//去掉最后一个","号
			int index = beansContent.lastIndexOf(",");
			beansContent = beansContent.substring(0,index);
			beansContent += "){\n";
			beansContent += "\t\tsuper();\n";
			for (String field : fields) {
				beansContent += "\t\tthis."+field+" = "+field+";\n";
			}
			beansContent += "\t}";
			//将实体类内容塞进实体类的java文件中
			PrintWriter write = new PrintWriter(beans);
			write.print(beansContent+"\n}");
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//生成dao包
	public void createDao(String className,Map<String,String> maps){
		Properties prop = new Properties();
		try {
			prop.load(CreateBeans.class.getResourceAsStream("/shyf.properties"));
			daoPackageName = packageName.replaceAll("\\.\\w+$", ".dao");
			String[] dirs = daoPackageName.split("\\.");
			String dirName="";
			for (String string : dirs) {
				dirName += "/"+string;
			}
			//创建dao包
			File daoPackage = new File("src"+dirName);
			daoPackage.mkdirs();
			//创建接口文件
			File dao = new File("src"+dirName+"/"+upFirst(className)+"Dao.java");
			dao.createNewFile();
			//拼接接口的内容
			String daoContent = "package "+daoPackageName+";\n";
			//因为数据库中经常会使用到date类型，为了防止出现少导包的情况，默认导入import java.util.Date包，这里有些不合理应该做一步判断，如果存在date的字段类型再去导入
			daoContent += "import java.util.Date;\n";
			//daoContent += "import "+packageName+"."+upFirst(className)+";\n";
			//拼接接口名
			daoContent += "public interface "+upFirst(className)+"Dao{\n";
			//将实体类内容塞进实体类的java文件中
			PrintWriter write = new PrintWriter(dao);
			write.print(daoContent+"\n}");
			write.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//生成impl包
	public void createImpl(String className,Map<String,String> maps){
		Properties prop = new Properties();
		try {
			prop.load(CreateBeans.class.getResourceAsStream("/shyf.properties"));
			String implPackageName = packageName.replaceAll("\\.\\w+$", ".dao.impl");
			String[] dirs = implPackageName.split("\\.");
			String dirName="";
			for (String string : dirs) {
				dirName += "/"+string;
			}
			//创建impl包
			File implPackage = new File("src"+dirName);
			implPackage.mkdirs();
			//创建实现类文件
			File impl = new File("src"+dirName+"/"+upFirst(className)+"Impl.java");
			impl.createNewFile();
			//拼接实现类的内容
			String daoContent = "package "+implPackageName+";\n";
			//因为数据库中经常会使用到date类型，为了防止出现少导包的情况，默认导入import java.util.Date包，这里有些不合理应该做一步判断，如果存在date的字段类型再去导入
			daoContent += "import java.util.Date;\n";
			daoContent += "import "+daoPackageName+"."+upFirst(className)+"Dao;\n";
			//拼接实现类名
			daoContent += "public class "+upFirst(className)+"Impl implements "+upFirst(className)+"Dao{\n";
			//将实现类内容塞进实体类的java文件中
			PrintWriter write = new PrintWriter(impl);
			write.print(daoContent+"\n}");
			write.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//获取数据库中所有表名
	public List<String> getTableNames(){
		Connection connection = JDBCutil.getConnection();
		List<String> tableNames = new ArrayList<String>();
		try {
			DatabaseMetaData dataBase = connection.getMetaData();
			String[] types = {"TABLE"};
			ResultSet set = dataBase.getTables(null, null, null, types);
			while(set.next()){
				String tableName = set.getString("TABLE_NAME");
				tableNames.add(tableName);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableNames;
	}
	//遍历存放表名的list，获取到每个表中的所有字段以及字段类型，调用create()方法生成对应的实体类
	public void createAll(){
		List<String> tableNames = getTableNames();
		Map<String, String> maps = new HashMap<String, String>();
		Connection connection = JDBCutil.getConnection();
		try {
			Statement sta = connection.createStatement();
			for (String tableName : tableNames) {
				String sql = "select * from "+tableName;
				ResultSet set = sta.executeQuery(sql);
				ResultSetMetaData dataSet = set.getMetaData();
				int columns = dataSet.getColumnCount();
				for(int i=0;i<columns;i++){
					String field = dataSet.getColumnName(i+1);
					String fieldType = dataSet.getColumnClassName(i+1);
					maps.put(field, dt.dataTypeTranform(lastDataTypeName(fieldType)));
				}
				createBeans(tableName, maps);
				createDao(tableName, maps);
				createImpl(tableName, maps);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//首字母大写的方法
	public String upFirst(String str){
		return str.substring(0,1).toUpperCase()+str.substring(1);
	}
	//获取到数据表中的字段类型后截取最后一部分名字
	public String lastDataTypeName(String str){
		int index = str.lastIndexOf(".");
		return str.substring(index+1);
	}
}
