package com.purediscovery.relativity;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.kcura.services.relativity_services._6.Artifact;
import com.kcura.services.relativity_services._6.ArtifactManager;
import com.kcura.services.relativity_services._6.IArtifactManager;
import com.kcura.services.relativity_services._6_2.query.Condition;
import com.kcura.services.relativity_services._6_2.query.Query;
import com.kcura.services.relativity_services._6_2.query.QueryResult;

import javax.xml.ws.BindingProvider;
import java.util.List;

/**
 * @author adamstrickland
 * @date Sep 14, 2010
 */
public class RelativityFacade {
//	public static final Log
	private static RelativityFacade self = null;

	public static final String DEFAULT_USERNAME = "relativity.admin@kcura.com";
	public static final String DEFAULT_PASSWORD = "Discovery1!";

	public static RelativityFacade getInstance(){
		return RelativityFacade.getInstance(RelativityFacade.DEFAULT_PASSWORD);
	}

	public static RelativityFacade getInstance(String password){
		return RelativityFacade.getInstance(RelativityFacade.DEFAULT_USERNAME, password);
	}

	public static RelativityFacade getInstance(String username, String password){
		if(self == null){
			self = new RelativityFacade(username, password);
		}

		return self;
	}

	private IArtifactManager artifactApi;
	private String username;
	private String password;
	private String token = null;

	private RelativityFacade(String username, String password){
		this.username = username;
		this.password = password;
		ArtifactManager artifactService = new ArtifactManager();
		artifactApi = artifactService.getRelativityIArtifactManager();
	}

	protected void login() throws Exception{
		((BindingProvider) artifactApi).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, this.username);
		((BindingProvider) artifactApi).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, this.password);
		this.token = artifactApi.login();
	}

	protected boolean isLoggedIn(){
		return this.token != null;
	}

	public List<Integer> query() throws Exception{
		return query(-1);
	}

	public List<Integer> query(int length) throws Exception{
		if(!isLoggedIn()){
			login();
		}
		Query query = new Query();
		Condition condition = new Condition();
//		JAXBElement<Condition> jCondition = new JAXBElement<Condition>();
//		query.setCondition(jCondition);
		QueryResult result = artifactApi.query(token, query, length);
		return Lists.transform(result.getQueryArtifacts().getValue().getArtifact(), new Function<Artifact, Integer>(){
			public Integer apply(Artifact a){
				return a.getArtifactID();
			}
		});
	}

	public void update() throws Exception{

	}
}
