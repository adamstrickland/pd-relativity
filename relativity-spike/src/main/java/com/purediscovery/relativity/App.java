package com.purediscovery.relativity;

import com.kcura.services.relativity_services._6.ArtifactManager;
import com.kcura.services.relativity_services._6.IArtifactManager;

import javax.xml.ws.BindingProvider;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main( String[] args )
	{
		System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		
		try{
			IArtifactManager port = new ArtifactManager().getRelativityIArtifactManager();
			((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "cks@purediscovery.com");
			((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "Discovery3!");

			String token = port.login();

			System.out.println(String.format("token is '%1$s'", token));

			port.logout(token);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
