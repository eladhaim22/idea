(function () {
	var module = angular.module('ideaApp');

	module.factory('projectService', ['entityServiceFactory','restService', function (entityServiceFactory,restService) {
		var projectService = entityServiceFactory.create('project');
		
		projectService.changeState = function(projectId,state){
			restService.post("api/project/changeState",projectId);
		}
		
		return projectService;
	}]);
})();