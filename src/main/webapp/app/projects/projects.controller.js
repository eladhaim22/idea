(function() {
    'use strict';

    angular
        .module('ideaApp')
        .controller('ProjectsController', ProjectsController);

    ProjectsController.$inject = ['$scope', 'Principal', 'LoginService', '$state','projectService','periodService','$q','evaluationService'];

    function ProjectsController ($scope, Principal, LoginService, $state,projectService,periodService,$q,evaluationService) {
    	var vm = this;
    	var UserIdentity;
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
    	
    	vm.getActiveStatus = function(project){
    		return _.filter(project.states,function(state){return state.active == 1})[0].status;
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
    	
    	intialize(null);
    }
})();
