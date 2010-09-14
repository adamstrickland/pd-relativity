package com.purediscovery.relativity;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author adamstrickland
 * @date Sep 14, 2010
 */
public class RelativityFacadeTest{
	RelativityFacade subject;

	@Test
	public void testGetInstance(){
		subject = RelativityFacade.getInstance();
		assertNotNull(subject);
	}
}
