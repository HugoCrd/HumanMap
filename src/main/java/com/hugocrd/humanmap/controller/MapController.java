package com.hugocrd.humanmap.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hugocrd.humanmap.dao.PoiDao;
import com.hugocrd.humanmap.model.Box;
import com.hugocrd.humanmap.model.Circle;
import com.hugocrd.humanmap.model.Loc;
import com.hugocrd.humanmap.model.Poi;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MapController {
	
	private static final Logger logger = LoggerFactory.getLogger(MapController.class);

	@Autowired
	private PoiDao poiDao;
	
	@RequestMapping(value = "poi/within/box/{limit}", method = RequestMethod.POST)
	public @ResponseBody Iterable<Poi> withinBox(@RequestBody Box box, @PathVariable int limit) {
		logger.info("Sending POIs within box "+box.getSouth()+":"+box.getWest()+" and "+box.getNorth()+":"+box.getEast());
		return poiDao.getWithin(box, limit);
	}
	
	@RequestMapping(value = "poi/within/circle/{limit}", method = RequestMethod.POST)
	public @ResponseBody Iterable<Poi> withinCircle(@RequestBody Circle circle, @PathVariable int limit) {
		logger.info("Sending POIs within circle "+circle.getLat()+":"+circle.getLng()+" with a "+circle.getRadius()+"radians radius");
		return poiDao.getWithin(circle, limit);
	}
	
	@RequestMapping(value = "poi/within/polygon/{limit}", method = RequestMethod.POST)
	public @ResponseBody Iterable<Poi> withinPolygon(@RequestBody Loc[] locs, @PathVariable int limit) {
		logger.info("Sending POIs within polygon");
		return poiDao.getWithin(locs, limit);
	}

	public void setPoiDao(PoiDao poiDao) {
		this.poiDao = poiDao;
	}
	
}
