## 简介
easyBeans是一个实现了从数据库到实体类的映射的一个插件
***
## 目的
在学习了servlet，mysql，jsp后做了一个项目，网上购物系统，在制作过程中，先创建好数据库，然后还得对应的将所有数据库中的表手动转化为实体类，这个工作只是一个抄写的过程感觉没有必要花太多时间，所以就想写一个工具，实现自动生成实体类。
***
## 基本原理
* 调用DatabaseMetaData里面的getTables()方法获取数据库中所有表名
* 调用ResultSetMetaData里面的getColumnName()方法和getColumnClassName()方法获取表中所有列名以及所对应的数据类型
* 根据表名生成对应的实体类文件，根据列名和数据类型确定实体类中的字段。
* 用拼字符串的方式，将实体类的内容拼接出来，通过输出流写入到实体类中
***
## 使用流程
1. 第一步：导入jar包，mysql-connector-java-5.0.5-bin.jar（因为依赖与jdbc）和easyBeans.jar
2. 调用new CreateProperties().create()方法生成配置文件
3. 调用new CreateBeans().createAll()方法生成实体类
