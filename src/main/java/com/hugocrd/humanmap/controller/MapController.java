package com.hugocrd.humanmap.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hugocrd.humanmap.dao.PoiDao;
import com.hugocrd.humanmap.model.Box;
import com.hugocrd.humanmap.model.Poi;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MapController {
	
	private static final Logger logger = LoggerFactory.getLogger(MapController.class);

	@Autowired
	private PoiDao poiDao;
	
	@RequestMapping(value = "poi/within/", method = RequestMethod.POST)
	public @ResponseBody Iterable<Poi> home(@RequestBody Box box) {
		logger.info("Sending POIs within "+box.getSouth()+":"+box.getWest()+" and "+box.getNorth()+":"+box.getEast());
		return poiDao.getWithin(box);
	}

	public void setPoiDao(PoiDao poiDao) {
		this.poiDao = poiDao;
	}
	
}
