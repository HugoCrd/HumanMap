'use strict';

/* App Module */

angular.module('humanMap', []).
	config(['$routeProvider', function($routeProvider) {
		  $routeProvider.
		  	when('/adjusting', {templateUrl: 'resources/partials/adjusting.html', controller: AdjustingCtrl}).
		  	when('/rectNcircles', {templateUrl: 'resources/partials/rectNcircles.html', controller: RectNcirclesCtrl}).
		    otherwise({redirectTo: '/adjusting'});
	}]);


function AdjustingCtrl($scope, $routeParams, $http, $location) {

	var map = L.map('adjustingMap').setView([48.85387, 2.34283], 12);	
	L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
	    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>',
	    maxZoom: 18
	}).addTo(map);
	
	var markers = L.layerGroup([]);
	map.addLayer(markers);
	map.on('load', function(){loadWithinBox(markers, map.getBounds());});
	map.on('moveend', function(){loadWithinBox(markers, map.getBounds());});
	
	function loadWithinBox(markers, bounds) {
		var southWest=bounds.getSouthWest();
		var northEast=bounds.getNorthEast();
		//Ajout des markers existants
	    $http.post('rest/poi/within/box',{'south':southWest.lat, 'west':southWest.lng, 'north':northEast.lat, 'east':northEast.lng})
	    .success(function(data, status, headers, config) {
	    	markers.clearLayers();
	    	angular.forEach(data, function(caption){
	    		L.marker([caption.loc.lat,caption.loc.lng]).bindPopup(caption.description).addTo(markers);
	    	});
	    })
		.error(function(data, status, headers, config){
			console.error('Oups, can\'t retreive POIs');
		});
	};
}

function RectNcirclesCtrl($scope, $routeParams, $http, $location) {
	
	var map = L.map('polyMap').setView([48.85387, 2.34283], 12);	
	L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
	    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>',
	    maxZoom: 18
	}).addTo(map);
	
	var markers = L.layerGroup([]);
	map.addLayer(markers);
	
	var drawControl = new L.Control.Draw({
		position: 'topleft',
		polygon: {
			title: 'Draw a sexy polygon!',
			allowIntersection: false,
			drawError: {
				color: '#b00b00',
				timeout: 1000
			},
			shapeOptions: {
				color: '#bada55'
			}
		},
		circle: {
			shapeOptions: {
			color: '#662d91'
			}
		}
	});
	map.addControl(drawControl);
	
	var drawnItems = new L.LayerGroup();
	map.on('draw:poly-created', function (e) {
		drawnItems.addLayer(e.poly);
	});
	map.on('draw:rectangle-created', function (e) {
		drawnItems.addLayer(e.rect);
	});
	map.on('draw:circle-created', function (e) {
		drawnItems.addLayer(e.circ);
		loadWithinCircle(e.circ.getLatLng(), e.circ.getRadius(), markers);
	});
	map.on('draw:marker-created', function (e) {
	e.marker.bindPopup('A popup!');
		drawnItems.addLayer(e.marker);
	});
	map.addLayer(drawnItems);
	
	function loadWithinCircle(latlng, radius, markers) {
		//Ajout des markers existants
	    $http.post('rest/poi/within/circle',{'lat':latlng.lat, 'lng':latlng.lng, 'radius':radius})
	    .success(function(data, status, headers, config) {
	    	markers.clearLayers();
	    	angular.forEach(data, function(caption){
	    		L.marker([caption.loc.lat,caption.loc.lng]).bindPopup(caption.description).addTo(markers);
	    	});
	    })
		.error(function(data, status, headers, config){
			console.error('Oups, can\'t retreive POIs');
		});
	};
	
}