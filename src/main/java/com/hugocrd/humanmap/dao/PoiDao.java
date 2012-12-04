package com.hugocrd.humanmap.dao;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hugocrd.humanmap.cloud.HumanMongoDbFactory;
import com.hugocrd.humanmap.model.Box;
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
	
	public Iterable<Poi> getWithin(Box box){
		/*
		 * From Mongo doc :
		 * 	> box = [[40.73083, -73.99756], [40.741404,  -73.988135]]
		 *	> db.places.find({"loc" : {"$within" : {"$box" : box}}})
		 * */
		String query = "{loc: {$within : {$box : "+box+"}}}";
		return pois.find(query).limit(100).as(Poi.class);
	}
	
}
