package com.kylin.test;

import java.sql.Connection;
import java.sql.SQLException;

import bitronix.tm.resource.jdbc.PoolingDataSource;

public class PoolingDataSourceTest {

	public static void main(String[] args) throws SQLException {

		PoolingDataSource ds = JBPMTestHelper.setupDataSource();
		
		ds.setMaxPoolSize(20);
		
		int index =0;
		
		try {
			for(;;) {
				index ++ ;
				Connection conn = ds.getConnection();
				System.out.println("Create Connection " +index + "  -> " + conn);
			}
		} catch (Exception e) {
			System.err.println("Mandatory made DataBase Crash " + e.getMessage());
		}
	}

}
