package com.hugocrd.humanmap.dao;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hugocrd.humanmap.cloud.HumanMongoDbFactory;
import com.hugocrd.humanmap.model.Box;
import com.hugocrd.humanmap.model.Circle;
import com.hugocrd.humanmap.model.Loc;
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
	
	public Iterable<Poi> getWithin(Box box, int limit){
		/*
		 * From Mongo doc :
		 * 	> box = [[40.73083, -73.99756], [40.741404,  -73.988135]]
		 *	> db.places.find({"loc" : {"$within" : {"$box" : box}}})
		 * */
		String query = "{loc: {$within : {$box : "+box+"}}}";
		return pois.find(query).limit(limit).as(Poi.class);
	}
	
	public Iterable<Poi> getWithin(Circle circle, int limit){
		/*
		 * From Mongo doc :
		 * 	> center = [50, 50]
		 *	> radius = 10
		 *	> db.places.find({"loc" : {"$within" : {"$centerSphere" : [center, radius]}}})
		 * */
		String query = "{loc: {$within : {$centerSphere : "+circle+"}}}";
		return pois.find(query).limit(limit).as(Poi.class);
	}
	
	public Iterable<Poi> getWithin(Loc[] locs, int limit){
		/*
		 * From Mongo doc :
		 * 	> polygonA = [ [ 10, 20 ], [ 10, 40 ], [ 30, 40 ], [ 30, 20 ] ]
		 *	> polygonB = { a : { x : 10, y : 20 }, b : { x : 15, y : 25 }, c : { x : 20, y : 20 } }
		 *	> db.places.find({ "loc" : { "$within" : { "$polygon" : polygonA } } })
		 *	> db.places.find({ "loc" : { "$within" : { "$polygon" : polygonB } } })
		 * */
		StringBuilder polygon= new StringBuilder("[");
		for(int i=0 ; i<locs.length ; i++) {
			if(i!=0) polygon.append(",");
			polygon.append(locs[i]);
		}
		polygon.append("]");
		String query = "{loc: {$within : {$polygon : "+polygon.toString()+"}}}";
		return pois.find(query).limit(limit).as(Poi.class);
	}
	
}
