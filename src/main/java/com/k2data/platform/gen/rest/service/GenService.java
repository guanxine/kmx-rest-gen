package com.k2data.platform.gen.rest.service;

import com.k2data.platform.gen.rest.dao.GenDao;
import com.k2data.platform.gen.rest.utils.GenUtils;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

public class GenService {

	private GenDao genDao;

	public GenService(GenDao genDao) {
		this.genDao = genDao;
	}

	public List<Map<String, Object>> queryList(Map<String, Object> map) {
		return genDao.queryList(map);
	}

	public int queryTotal(Map<String, Object> map) {
		return genDao.queryTotal(map);
	}

	public Map<String, String> queryTable(String tableName) {
		return genDao.queryTable(tableName);
	}

	public List<Map<String, String>> queryColumns(String tableName) {
		return genDao.queryColumns(tableName);
	}

	public byte[] generatorCode(String[] tableNames) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);

		for(String tableName : tableNames){
			//查询表信息
			Map<String, String> table = queryTable(tableName);
			//查询列信息
			List<Map<String, String>> columns = queryColumns(tableName);
			//生成代码
			GenUtils.generatorCode(table, columns, zip);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}
}
