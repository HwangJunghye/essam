package com.essam.www.file;

import com.essam.www.bean.FileBean;

public interface IFileDao {
	// file-mapper.xml 사용

	FileBean getFileData(String fileNo);
	boolean saveFile(FileBean fileBean);
	boolean deleteFile(String fileNo);
}
