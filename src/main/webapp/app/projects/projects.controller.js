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
    		
    		projectService.getAll().then(function(projects){
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
