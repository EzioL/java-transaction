### step1
- 知道了Java事务是作用于JDBC的Connection之上的,每一个Connection其实都是一个事务的（这样说好像不太对，还是看下面的示例代码吧
  
  ````
  try{
    connection.setAutoCommit(false);
    // do something
    connection.commit();
  }catch (SQLException e) {
    connection.rollback();
  }
  ````
- 如果是是一个连续的操作(比如打款)，下面有多个数据库操作，如果他们是多个Connection的话，一个操作失败会rollback，其他的不影响，
也就说多个Connection是多个事务。
测试中 有三个Connection，外层的Connection是手动提交，设置回滚，扣款的Connection和充值的Connection都是自动提交的。
先扣款再充值，充值操作失败的话，扣款操作没有回滚...

