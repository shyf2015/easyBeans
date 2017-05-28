package com.shyf.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class JDBCutil {
	public static Connection getConnection(){
		try {
			Properties prop = new Properties();
			prop.load(JDBCutil.class.getResourceAsStream("/shyf.properties"));
			//读取配置信息
			String driver = prop.getProperty("driver");
			String url = prop.getProperty("url");
			String username = prop.getProperty("username");
			String password = prop.getProperty("password");
			//加载驱动
			Class.forName(driver);
			//建立连接
			Connection connection = DriverManager.getConnection(url,username,password);
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
