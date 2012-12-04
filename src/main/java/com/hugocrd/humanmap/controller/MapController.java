package com.hugocrd.humanmap.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hugocrd.humanmap.dao.PoiDao;
import com.hugocrd.humanmap.model.Poi;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MapController {
	
	private static final Logger logger = LoggerFactory.getLogger(MapController.class);

	@Autowired
	private PoiDao poiDao;
	
	@RequestMapping(value = "poi/between/{south}/{west}/{north}/{east}", method = RequestMethod.GET)
	public @ResponseBody List<Poi> home(@PathVariable BigDecimal south, @PathVariable BigDecimal west, @PathVariable BigDecimal north, @PathVariable BigDecimal east) {
		logger.info("Sending POIs between "+south+":"+west+" and "+north+":"+east);
		List<Poi> pois = new ArrayList<Poi>();
		pois.add(new Poi("Descr", BigDecimal.valueOf(48.853622), BigDecimal.valueOf(2.36381)));
		return pois;
	}

	public void setPoiDao(PoiDao poiDao) {
		this.poiDao = poiDao;
	}
	
}
