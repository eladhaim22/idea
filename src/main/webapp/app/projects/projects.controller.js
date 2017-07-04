(function() {
    'use strict';

    angular
        .module('ideaApp')
        .controller('ProjectsController', ProjectsController);

    ProjectsController.$inject = ['$scope', 'Principal', 'LoginService', '$state','projectService','periodService','$q','evaluationService'];

    function ProjectsController ($scope, Principal, LoginService, $state,projectService,periodService,$q,evaluationService) {
    	var vm = this;
    	var UserIdentity;
    	vm.status;
    	vm.currentPage = 1;
    	vm.itemsPerPage = 20;
    	vm.projectCounter = [
			{
				label:'Iniciado',
				count:0,
				class:'default'
			},
			{
				label:'Pré-Selecionado',
				count:0,
				class:'info'
			},
			{
				label:'Rechazado',
				count:0,
				class:'danger'
			},
			{
				label:'Etapa Final',
				count:0,
				class:'success'
			}
    	];
    	
    	vm.getActiveStatus = function(project){
    		return projectService.getStatusSpanish(_.filter(project.states,function(state){return state.active == 1})[0].status);
    	}
    	
    	vm.changePage = function(){
    		setPaginetedProjects(vm.currentPage);
    	}
    	
    	function setPaginetedProjects(page) {
    		vm.paginatedProjects = vm.filterdProjects.slice(vm.itemsPerPage * (page - 1),vm.itemsPerPage * (page - 1) + vm.itemsPerPage);
    	}
    	
    	function intialize(period_id){
			Principal.identity().then(function(identity){
				UserIdentity = identity;
				var promises = {
						projects : projectService.getAllWithPeriod(period_id)}
				if(vm.hasAuthority("ROLE_ADMIN")){
					promises.periods = periodService.getAll();
				}
				else {
					if(vm.hasAuthority("ROLE_REFERRE")){
						promises.evaluations = evaluationService.get("/ByReferre");
					}
				}
				$q.all(promises).then(function(data){
	    			vm.projects = data.projects;
	    			vm.filterdProjects = vm.projects;
	    			setPaginetedProjects(vm.currentPage);
	    			
	    			_.forEach(vm.projectCounter,function(status){
	    				var count = _.countBy(vm.projects,function(project){return vm.getActiveStatus(project) == status.label ? 1 : 0})[1];
	    				status.count =  count ? count : 0;
	    			});
	    			
	    			if (vm.hasAuthority("ROLE_ADMIN")){
		    			var defaultPeriod = data.periods.splice(_.indexOf(data.periods,_.filter(data.periods,function(period){return period.active})))[0];
		    			vm.periods = _.sortBy(data.periods,'startingDate');
		    			vm.periods.unshift(defaultPeriod);
		    			angular.forEach(vm.periods,function(period){
		    				period.startingDate = period.startingDate.format("DD/MM/YYYY");
		    				period.endingDate = period.endingDate.format("DD/MM/YYYY");
		    			});
		    			vm.selectedPeriod = !vm.selectedPeriod ? defaultPeriod : vm.selectedPeriod; 
	    			}
	    			else{
	    				if(vm.hasAuthority("ROLE_REFERRE")){
	    					var evaluations = data.evaluations;
	    					_.forEach(vm.projects,function(project){
	    	    				if(_.any(project.evaluationsIds,function(e){return _.contains(_.map(evaluations,"id"),e)})){
	    	    					project.hasEvaluated = true;
	    	    				}
	    	    				else
	    	    					project.hasEvaluated = false;
	    	    			});
	    				}
	    			}
	    		},function(error){
	    			
	    		});
			},function(error){
				
			});
    	}
    	
    	vm.hasAuthority = function(role){
    		return _.contains(UserIdentity.authorities,role);
    	}
    	
    	vm.hasAnyAuthority = function(roles){
    		var hasAuthority = false;
    		_.forEach(roles,function(role){
    			if(!hasAuthority){
    				hasAuthority = _.contains(UserIdentity.authorities,role);
    			}
    		});
    		return hasAuthority;
    	}
    	
    	vm.changePeriod = function(){
    		intialize(vm.period_id);
    	}
    	
    	vm.navigateToProject = function(id){
    		$state.go('project',{id:id});
    	}
    	
    	vm.change = function(){
    		var period = _.filter(vm.periods,function(p){return p.id == vm.selectedPeriod.id})[0];
    		if(!period.active)
    			intialize(period.id);
    		else
    			intialize(null);
    	}
    	
    	vm.projectStatus = function(project){
    		return _.filter(project.states,function(state){return state.active})[0].status;
    	}
    	
    	
    	vm.changeList = function(){
    		if(!vm.status){
    			vm.filterdProjects = vm.projects;
    		}
    		else{
    			vm.filterdProjects = _.filter(vm.projects,function(project){return vm.getActiveStatus(project) == vm.status.label});
    		}
    		setPaginetedProjects(1);
    	}
    	
    	intialize(null);
    }
})();
