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
        vm.edit = $state.params.id ? true : false;
    	
    	
    	function intialize(){
    		if($state.params.id){
    			projectService.get($state.params.id).then(function(project){
    				vm.project = project;
    				vm.projectState = _.find(project.states, function(state){ return state.active === true });
    			},function(error){
    				
    			});
    		}
    	}
    	
    	vm.changeState = function (){
    		projectService.changeState(vm.project.id).then(function(success){
    			$state.reload();
    		},function(error){
    			
    		});
    	}
    	
        vm.addPersonToProject = function(){
        	if(vm.person.type){
	        	vm.project.team.push(angular.copy(vm.person));
	        	vm.person = {};
	        	vm.personType = undefined;
	        }
        }
        
        vm.deletePersonFromProject = function(index){
        	vm.project.team.splice(index);
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
