(function () {
	var module = angular.module('ideaApp');

	module.factory('evaluationService', ['entityServiceFactory','restService','User', function (entityServiceFactory,restService) {
		var evaluationService = entityServiceFactory.create('evaluation');
		
		
		return evaluationService;
	}]);
})();