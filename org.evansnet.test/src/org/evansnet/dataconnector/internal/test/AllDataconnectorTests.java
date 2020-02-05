package org.evansnet.dataconnector.internal.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CredentialsTest.class, 
				MySQLConnectionTest.class, 
				SQLConnectorTest.class })
public class AllDataconnectorTests {

}
