package com.essam.www.test;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Alias("testbean")
@Getter
@Setter
@Accessors(chain = true)
public class TestBean {
	private int a;
	private int b;
}
