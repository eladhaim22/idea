(function() {
    'use strict';

    angular
        .module('ideaApp')
        .controller('ProjectsController', ProjectsController);

    ProjectsController.$inject = ['$scope', 'Principal', 'LoginService', '$state','projectService','periodService','$q'];

    function ProjectsController ($scope, Principal, LoginService, $state,projectService,periodService,$q) {
    	var vm = this;
    	
    	function intialize(period_id){
    		var promises = {
    					projects : projectService.getAllWithPeriod(period_id)}
    		Principal.hasAuthority('ROLE_ADMIN').then(function(admin){
    			if(admin){
    				promises.periods = periodService.getAll()
    			}
    			$q.all(promises).then(function(data){
        			vm.projects = data.projects;
        			angular.forEach(vm.projects,function(project){
        				project.createdDate = new moment(project.createdDate).format("DD/MM/YYYY");
        			});
        			if (admin){
    	    			var defaultPeriod = data.periods.splice(_.indexOf(data.periods,_.filter(data.periods,function(period){return period.active})))[0];
    	    			vm.periods = _.sortBy(data.periods,'startingDate');
    	    			vm.periods.unshift(defaultPeriod);
    	    			angular.forEach(vm.periods,function(period){
    	    				period.startingDate = period.startingDate.format("DD/MM/YYYY");
    	    				period.endingDate = period.endingDate.format("DD/MM/YYYY");
    	    			});
    	    			vm.periodId = vm.periodId ? defaultPeriod.id : vm.periodId; 
        			}
        		},function(error){
        			
        		});
			},function(error){
				
			});
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
    		var period = _.filter(vm.periods,function(p){return p.id == vm.periodId})[0];
    		if(!period.active)
    			intialize(period.id);
    		else
    			intialize(null);
    	}
    	
    	intialize(null);
    }
})();
