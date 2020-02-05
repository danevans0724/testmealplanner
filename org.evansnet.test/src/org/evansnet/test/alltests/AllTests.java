package org.evansnet.test.alltests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import org.evansnet.common.test.*;
import org.evansnet.dataconnector.internal.test.*;
import org.evansnet.ingredient.test.*;


@RunWith(Suite.class)
@SuiteClasses({AllCommonTests.class, AllDataconnectorTests.class, AllRepositoryTests.class})
public class AllTests {
	
	

}
