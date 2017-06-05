(function() {
    'use strict';

    angular
        .module('ideaApp')
        .controller('ProjectsController', ProjectsController);

    ProjectsController.$inject = ['$scope', 'Principal', 'LoginService', '$state','projectService'];

    function ProjectsController ($scope, Principal, LoginService, $state,projectService) {
    	var vm = this;
    	
    	function intialize(){
    		Principal.identity().then(function(identity){
    		if(_.contains(identity.authorities,"ROLE_ADMIN")){
    			var promise = projectService.getAll();
    		}
    		else{
    			if(_.contains(identity.authorities,"ROLE_REFERRE")){
    				var promise = projectService.getByReferre();
    			}
    			else
    				{
    					var promise = projectService.getByUser();
    				}
    		}
    		promise.then(function(projects){
    			vm.projects = projects;
    		},function(error){
    			
    		});
    		
    		},function(error){
    			
    		});
    	}
    	
    	vm.navigateToProject = function(id){
    		$state.go('project',{id:id});
    	}
    	
    	intialize();
    }
})();
