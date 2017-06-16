(function () {
	var module = angular.module('ideaApp');

	module.factory('templateService', ['entityServiceFactory','restService', function (entityServiceFactory,restService) {
		var templateService = entityServiceFactory.create('template');
		
		templateService.getByName = function(name){
			return restService.get("api/template/byName/" + name);
		}
		
		return templateService;
	}]);
})();