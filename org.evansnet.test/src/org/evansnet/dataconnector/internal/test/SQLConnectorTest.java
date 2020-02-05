package org.evansnet.dataconnector.internal.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.easymock.PowerMock.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.evansnet.dataconnector.internal.core.DBType;
import org.evansnet.dataconnector.internal.dbms.ConnectionStrFactory;
import org.evansnet.dataconnector.internal.dbms.SQLSrvConnection;

import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.logging.Logger;


@PowerMockIgnore({"javax.net.ssl.*","javax.security.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({ SQLSrvConnection.class, DriverManager.class, ConnectionStrFactory.class})
public class SQLConnectorTest extends BaseTestConfig {

	public static Logger sqlTestLogger = Logger.getLogger("SQLConnectorTestLogger");

	SQLSrvConnection dbInst;
	String expected = "jdbc:sqlserver://localhost:1433;databaseName=DCEDB01;user=Dan;password=thePwd"; 

	
	
	@Before
	public void setup() throws Exception {
		
		dbInst = new SQLSrvConnection(hostMock);
		dbInst.getHost().setHostName("localhost");
		dbInst.getHost().setPort(1433);
		dbInst.setInstanceName(dbInst.getHost().getHostName());
		dbInst.setDatabaseName("DCEDB01");
		dbInst.setCredentials(credentialsMock);
		
	}
	
	
	@Test
	public void testBuildConnectionString() {
		try {
			String result = dbInst.buildConnectionString(DBType.MS_SQLSrv);
			assertTrue(result.equals(expected));
			PowerMock.verify(connectionStringFactoryMock);
		} catch (Exception e) {
			fail("Build connection string test failed! Cause: " + e.getCause());
		}		
	}
	
	
	@Test
	public void testConnect() {
		try {
			Connection testConnection = dbInst.connect(expected);
			Connection theSetConnection = dbInst.getConnection();
			assertTrue(testConnection == connectionMock);
			assertTrue(theSetConnection == connectionMock);
			verify(driverManagerMock);
		} catch (SQLException e) {
			fail("Test of SQLConnector.connect() failed! SQLException thrown: " + e.getMessage());
		} catch (Exception e) {
			fail("Test of SQLSrvConnection.connect() failed! A general exception was thrown: " + e.getMessage());
		}
	}

	
	//Helper method
//	private Certificate fetchCertificate() throws Exception {
//		String certFile = "C:\\Users\\pmidce0\\git\\testmealplanner\\org.evansnet.test\\TestObjects\\credentialsMock.cer";
//		FileInputStream fis = new FileInputStream (certFile);
//		return CertificateFactory.getInstance("X.509").generateCertificate(fis);
//	}

//	@Test
//	public void testGetSQLConnection() throws SQLException {
//		try {
//			setup();
//			String connString = dbInst.buildConnectionString(dbInst.getDBMS());
//			Connection conn = dbInst.connect(connString);
//			assertTrue(!conn.isClosed());
//			conn.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail("Connection to database failed.");
//		}
//	}

}
