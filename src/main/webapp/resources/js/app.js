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
	//Initialisation de la carte
	var map = L.map('adjustingMap').setView([48.85387, 2.34283], 12);	
	L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
	    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>',
	    maxZoom: 18
	}).addTo(map);
	
	var markers = L.layerGroup([]);
	map.addLayer(markers);
	map.on('moveend', function(e) {
		var southWest=map.getBounds().getSouthWest();
		var northEast=map.getBounds().getNorthEast();
		//Ajout des markers existants
		console.log('rest/poi/within/'+southWest.lat+'/'+southWest.lng+'/'+northEast.lat+'/'+northEast.lng);
	    $http.post('rest/poi/within/',{'south':southWest.lat, 'west':southWest.lng, 'north':northEast.lat, 'east':northEast.lng})
	    .success(function(data, status, headers, config) {
	    	markers.clearLayers();
	    	jQuery.each(data,function(i,caption){
	    		L.marker([caption.loc.lat,caption.loc.lng]).bindPopup(caption.description).addTo(markers);
	    	});
	    })
		.error(function(data, status, headers, config){
			console.error('Oups, can\'t retreive POIs');
		});
	});
}

function RectNcirclesCtrl($scope, $routeParams, $http, $location) {

}