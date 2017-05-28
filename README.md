#easyBeans是一个实现了从数据库到实体类的映射的一个插件

起因：在学习了servlet，mysql，jsp后做了一个项目，网上购物系统，在制作过程中，先创建好数据库，然后还得对应的将所有数据库中的表手动转化为实体类，这个工作只是一个抄写的过程感觉没有必要花太多时间，而且一不小心抄错了，影响很大，所以就想着写一个工具，让代码帮我生成实体类。

基本原理：先获取数据库中所有表名，根据表名确定对应的实体类名，创建对应的实体类文件，然后遍历每一张表获取表中所有列名，生成对应的实体类的字段，同时获取每一列的数据类型，生成对应的java数据类型。拼接字符串的方式，将实体类类中的内容字段，set方法，get方法，构造方法拼接出来，文件输出流的方式，将整个字符串输出到实体类的java文件中，同样的方式生成接口和接口实现类。

使用流程：导入jar包，mysql-connector-java-5.0.5-bin.jar和easyBeans.jar
                    调用new CreateProperties().create()方法生成配置文件
                    调用new CreateBeans().createAll()方法生成实体类