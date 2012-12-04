package com.hugocrd.humanmap.dao;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hugocrd.humanmap.cloud.HumanMongoDbFactory;
import com.hugocrd.humanmap.model.Poi;

@Repository
public class PoiDao {

	private MongoCollection pois;
	
	@Autowired
	public PoiDao(HumanMongoDbFactory mongoDbFactory){
		Jongo jongo = new Jongo(mongoDbFactory.getDbInfo());
		pois = jongo.getCollection("pois");
		pois.ensureIndex("{loc: '2d'}");
	}
	
	public Iterable<Poi> get(){
		return pois.find("{}").as(Poi.class);
	}
	
}
