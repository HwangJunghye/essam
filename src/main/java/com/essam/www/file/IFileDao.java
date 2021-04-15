package com.essam.www.file;

import com.essam.www.bean.FileBean;

public interface IFileDao {

	FileBean getFileData(String fileNo);
	// file-mapper.xml 사용
}
