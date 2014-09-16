package com.hcb.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hcb.mc.vo.User;

public interface UserDao {
	
	int countAll();
	
	List<User> selectAll();
	
	int insert(User user);
	
	int update(User user);
	
	int selectGetLockByKey(@Param("key")String key, @Param("timeout")int timeout);
}
