(function () {
	var module = angular.module('ideaApp');

	module.factory('projectService', ['entityServiceFactory','restService','User', function (entityServiceFactory,restService,User) {
		var projectService = entityServiceFactory.create('project');
		
		var statusSpanish = {
				'Initial':'Iniciado',
				'PreSelected':'Pré-Selecionado',
				'Rejected':'Rechazado',
				'FinalStage':'Etapa Final'
		};
		
		projectService.changeState = function(projectId,referres,state,comment){
			return restService.post("api/project/changeState",{"projectId" : projectId,"users": referres,"status":state,"comment":comment});
		}

		projectService.getAllWithPeriod = function(period_id){
			if(period_id)
				return projectService.get("",{headers: {Period_Id: period_id}});
			else
				return projectService.get("");
		}
		
		projectService.getStatusSpanish = function(status){
			return statusSpanish[status];
		}
		
		projectService.fill = entityServiceFactory.buildFill(null, function () {
			return [User.query()];
		}, function (project, users) {
			project.Users = _.map(project.usersIds, function (userId) { return angular.copy(_.find(users[0],function(user){return user.id == userId}))});
			project.createdDate = new moment(project.createdDate).format("DD/MM/YYYY");
		});
		

		
		return projectService;
	}]);
})();