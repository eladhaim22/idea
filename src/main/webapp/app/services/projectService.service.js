(function () {
	var module = angular.module('ideaApp');

	module.factory('projectService', ['entityServiceFactory', function (entityServiceFactory) {
		var projectService = entityServiceFactory.create('project');
		
		return projectService;
	}]);
})();