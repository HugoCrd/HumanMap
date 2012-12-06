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

	$scope.limit=400;
	var map = L.map('adjustingMap');
	var markers = L.layerGroup([]);
	map.on('load', function(){loadWithinBox(map.getBounds(), markers);});
	map.addLayer(markers);
	
	map.setView([48.85387, 2.34283], 17);
	L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
	    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>',
	    maxZoom: 18
	}).addTo(map);
	map.on('moveend', function(){loadWithinBox(map.getBounds(), markers);});
	
	function loadWithinBox(bounds, markers) {
		var southWest=bounds.getSouthWest();
		var northEast=bounds.getNorthEast();
		//Ajout des markers existants
	    $http.post('rest/poi/within/box/'+$scope.limit,{'south':southWest.lat, 'west':southWest.lng, 'north':northEast.lat, 'east':northEast.lng})
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
	
	$scope.limit=2000;
	var map = L.map('polyMap').setView([48.85387, 2.34283], 13);	
	L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
	    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>',
	    maxZoom: 18
	}).addTo(map);
	
	var markers = L.layerGroup([]);
	map.addLayer(markers);
	
	var drawControl = new L.Control.Draw({
		position: 'topleft',
		polyline: false,
		marker: false,
		polygon: {
	        shapeOptions: {
	            color: '#434343'
	        }
	    },
	    rectangle: {
	        shapeOptions: {
	            color: '#434343'
	        }
	    },
	    circle: {
	        shapeOptions: {
	            color: '#434343'
	        }
	    }
	});
	map.addControl(drawControl);
	
	var drawnItems = new L.LayerGroup();
	map.on('draw:poly-created', function (e) {
		drawnItems.addLayer(e.poly);
		console.log(e.poly);
		loadWithinPolygon(e.poly.getLatLngs(), markers);
	});
	map.on('draw:rectangle-created', function (e) {
		drawnItems.addLayer(e.rect);
		loadWithinBox(e.rect.getBounds(), markers);
	});
	map.on('draw:circle-created', function (e) {
		drawnItems.addLayer(e.circ);
		loadWithinCircle(e.circ.getLatLng(), e.circ.getRadius(), markers);
	});
	map.addLayer(drawnItems);
	
	function loadWithinCircle(latlng, radius, markers) {
		//Ajout des markers existants
	    $http.post('rest/poi/within/circle/'+$scope.limit,{'lat':latlng.lat, 'lng':latlng.lng, 'radius':radius/6378000})
	    .success(function(data, status, headers, config) {
	    	angular.forEach(data, function(caption){
	    		L.marker([caption.loc.lat,caption.loc.lng]).bindPopup(caption.description).addTo(markers);
	    	});
	    })
		.error(function(data, status, headers, config){
			console.error('Oups, can\'t retreive POIs');
		});
	};
	
	function loadWithinBox(bounds, markers) {
		var southWest=bounds.getSouthWest();
		var northEast=bounds.getNorthEast();
		//Ajout des markers existants
	    $http.post('rest/poi/within/box/'+$scope.limit,{'south':southWest.lat, 'west':southWest.lng, 'north':northEast.lat, 'east':northEast.lng})
	    .success(function(data, status, headers, config) {
	    	angular.forEach(data, function(caption){
	    		L.marker([caption.loc.lat,caption.loc.lng]).bindPopup(caption.description).addTo(markers);
	    	});
	    })
		.error(function(data, status, headers, config){
			console.error('Oups, can\'t retreive POIs');
		});
	};
	
	function loadWithinPolygon(latLngs, markers) {
		//Ajout des markers existants
	    $http.post('rest/poi/within/polygon/'+$scope.limit,JSON.stringify(latLngs))
	    .success(function(data, status, headers, config) {
	    	angular.forEach(data, function(caption){
	    		L.marker([caption.loc.lat,caption.loc.lng]).bindPopup(caption.description).addTo(markers);
	    	});
	    })
		.error(function(data, status, headers, config){
			console.error('Oups, can\'t retreive POIs');
		});
	};
	
}