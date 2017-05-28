package com.shyf.createbeans;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class CreateProperties {
	public void create(String url,String username,String password){
		File properties = new File("src/shyf.properties");
		try {
			properties.createNewFile();
			String propertiesContent = "beansPackageName=com.sram.beans\n";
			propertiesContent += "driver=com.mysql.jdbc.Driver\n";
			propertiesContent += "url="+url+"\n";
			propertiesContent += "username="+username+"\n";
			propertiesContent += "password="+password+"\n";
			PrintWriter write = new PrintWriter(properties);
			write.print(propertiesContent);
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
