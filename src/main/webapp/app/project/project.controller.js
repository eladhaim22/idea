(function() {
    'use strict';

    angular
        .module('ideaApp')
        .controller('ProjectController', ProjectController);

    ProjectController.$inject = ['$scope', '$state','projectService'];

    function ProjectController ($scope,$state,projectService) {
    	var vm = this;
    	vm.stages = ['1er año','2do año','3er año','4to año','5to año','Graduado','Post Grado'];
    	vm.person ={};
    	vm.project = {};
    	vm.project.team = [];
        
    	function intialize(){
    		if($state.params.id){
    			projectService.get($state.params.id).then(function(project){
    				vm.project = project;
    			},function(error){
    				
    			});
    		}
    	}
    	
        vm.addPersonToProject = function(){
        	if(vm.person.type){
	        	vm.project.team.push(angular.copy(vm.person));
	        	vm.person = {};
	        }
        }
        
        vm.save = function(){
        	projectService.save(vm.project).then(function(){
        		
        	},function(error){
        		console.log(error);
        	});
        }
        
        intialize(); 
    }
})();
