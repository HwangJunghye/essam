package com.essam.www.interceptor;

import org.apache.ibatis.annotations.Param;

public interface IInterceptorDao {

	boolean hasClassAuth(@Param("mbId") String mbId, @Param("clsNo") String clsNo, @Param("mbType") int mbType);

}
