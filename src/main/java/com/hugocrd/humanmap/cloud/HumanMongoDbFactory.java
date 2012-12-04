package com.hugocrd.humanmap.cloud;

import java.lang.reflect.Constructor;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.cloudfoundry.runtime.env.AbstractServiceInfo;
import org.cloudfoundry.runtime.env.CloudEnvironment;
import org.cloudfoundry.runtime.env.CloudServiceException;
import org.cloudfoundry.runtime.env.MongoServiceInfo;
import org.cloudfoundry.runtime.service.document.MongoServiceCreator;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.DB;
import com.mongodb.MongoURI;


public class HumanMongoDbFactory {

	static Logger logger = Logger.getLogger(HumanMongoDbFactory.class); 
	private MongoDbFactory mongoFactoryInfo;
	
	public HumanMongoDbFactory(){
		CloudEnvironment environment = new CloudEnvironment();
		try {
			if (!environment.isCloudFoundry()) {
				logger.info("Configuration de la base de donnée : Contexte local");
				
				mongoFactoryInfo = new SimpleMongoDbFactory(new MongoURI("mongodb://127.0.0.1:27017/yidsInfo"));
			} else {
				logger.info("Configuration de la base de donnée : Contexte cloud");
				
				MongoServiceInfo serviceInfo = getServiceInfo(getServiceDataByName(environment, "yidsInfo"), MongoServiceInfo.class);
				mongoFactoryInfo = new MongoServiceCreator().createService(serviceInfo);
			}
		} catch (UnknownHostException e) {
			logger.error("Impossible de se connecter à la base de donnée choisie");
			e.printStackTrace();
		}
		
	}
	
	public DB getDbInfo(){
		return mongoFactoryInfo.getDb();
	}
	
	
	/* Can't have CloudFoundry stuff working... This code works (just passing through service type validation) */
	private Map<String, Object> getServiceDataByName(CloudEnvironment environment, String name) {
		List<Map<String, Object>> services = environment.getServices();
		
		for (Map<String, Object> service : services) {
			if (service.get("name").equals(name)) {
				return service;
			}
		}
		return null;
	}
	
	/* Can't have CloudFoundry stuff working... This code works (just passing through service type validation) */
	private <T extends AbstractServiceInfo> T getServiceInfo(Map<String,Object> serviceInfoMap, Class<T> serviceInfoType) {
		try {
			Constructor<T> ctor = serviceInfoType.getConstructor(Map.class);
			return ctor.newInstance(serviceInfoMap);
		} catch (Exception e) {
			throw new CloudServiceException("Failed to create service information for " + serviceInfoMap.get("name"), e);
		}
	}
	
}
