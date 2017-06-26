(function () {
	var module = angular.module('ideaApp');

	module.factory('excelService', ['entityServiceFactory', function (entityServiceFactory) {
		var projectService = entityServiceFactory.create('excel');
		
		return projectService;
	}]);
})();