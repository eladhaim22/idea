(function () {
	var module = angular.module('ideaApp');

	module.factory('rankingService', ['entityServiceFactory', function (entityServiceFactory) {
		var rankingService = entityServiceFactory.create('ranking');
	
		return rankingService;
	}]);
})();