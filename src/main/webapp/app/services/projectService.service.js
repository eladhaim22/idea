(function () {
	var module = angular.module('ideaApp');

	module.factory('projectService', ['entityServiceFactory','restService','User', function (entityServiceFactory,restService,User) {
		var projectService = entityServiceFactory.create('project');
		
		projectService.changeState = function(projectId,referres,state){
			return restService.post("api/project/changeState",{"projectId" : projectId,"users": referres,"status":state});
		}

		projectService.fill = entityServiceFactory.buildFill(null, function () {
			return [User.query()];
		}, function (project, users) {
			project.Users = _.map(project.usersIds, function (userId) { return angular.copy(_.find(users[0],function(user){return user.id == userId})); });
			project.createdDate = new moment(project.createdDate).format("D/MM/YYYY");;
		});
		

		
		return projectService;
	}]);
})();