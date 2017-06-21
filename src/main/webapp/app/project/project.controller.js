(function() {
    'use strict';

    angular
        .module('ideaApp')
        .controller('ProjectController', ProjectController);

    ProjectController.$inject = ['$scope', '$state','projectService','User','$uibModal','templateService','$q'];

    function ProjectController ($scope,$state,projectService,User,$uibModal,templateService,$q) {
    	var vm = this;
    	vm.stages = ['1er año','2do año','3er año','4to año','5to año','Graduado','Post Grado'];
    	vm.person ={};
    	vm.project = {};
    	vm.project.team = [];
        vm.project.answers = [];
    	vm.edit = $state.params.id ? true : false;
    	
    	var states = ['Initial','PreSelected','Rejected','FinalStage'];
    	
    	function intialize(){
    		var promises = [templateService.getByName('Formulario_Inscripcion')];
    		if($state.params.id)
    			promises.push(projectService.get($state.params.id));
    		$q.all(promises).then(function(templateAndProject){
    			if($state.params.id){
    				vm.project = templateAndProject[1];
    				vm.projectState = _.find(templateAndProject[1].states, function(state){ return state.active === true });
    			}
    			vm.template = templateAndProject[0];
    			vm.sectionAndSubSection = [];
    			angular.forEach(_.toArray(_.groupBy(templateAndProject[0].questions,'section')),function(value){
    				vm.sectionAndSubSection.push(_.toArray(_.groupBy(value,'subsection')));
    			});
    			if(!$state.params.id){
	    			_.each(templateAndProject[0].questions,function(question){
						vm.project.answers.push({id:null,questionId : question.id,questionAnswer:undefined})
					});
    			}
    		},function(error){
    			
    		});
    	}
    	
    	 vm.calculateAnswerIndex = function(id){
    		return _.indexOf(vm.project.answers,_.findWhere(vm.project.answers,{questionId:id}));
    	}
    	
    	vm.changeState = function (state){
    		if(state == 'PreSelected'){
	    		openModal().result.then(function (project) {
		        	projectService.changeState(project.id,project.usersIds,_.indexOf(states,state)).then(function(data){
		        		$state.go('projects');
		        	},function(error){
		        		console.log('error');
		        	});
		        }, function () {
		        	console.log('Modal dismissed at: ' + new Date());
		        });
    		}
    		else if(state == 'Rejected'){
    			projectService.changeState(vm.project.id,null,_.indexOf(states,state)).then(function(data){
	        		$state.go('projects');
	        	},function(error){
	        		console.log('error');
	        	});
    		}
    	}
    	
    	
    	vm.getAnswerNode = function(questionId){
    		return _.filter(vm.project.answers,function(answer){
    			return answer.questionId = questionId;
    		})[0].questionAnswer;
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
        		$state.go('projects');
        	},function(error){
        		console.log(error);
        	});
        }
        
        vm.navigateToEvaluation = function(){
        	$state.go('evaluation',{projectId:$state.params.id});
        }
        
        function openModal (){
        	var scope = $scope.$new(true);
        	scope.project = angular.copy(vm.project);
    		var modalInstance = $uibModal.open({
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            scope:scope,
            backdrop: 'static',
            size:'lg',
            templateUrl: 'app/components/modal/projectReferreModal.html',
            controller: ['$scope','User','$uibModalInstance', function($scope,User,$uibModalInstance){            	
            	function initialize(){
            		User.query({},function(data){
            			$scope.users = _.filter(data, function(user){
                    	    return _.find($scope.project.Users, function(userInProject){
                    	        return userInProject.id !== user.id && _.contains(user.authorities,'ROLE_REFERRE');
                    	    });
            			});
            		},function(error){
            			
            		});
            	}
            	
            	$scope.isReferee = function(user){
            		return _.contains(user.authorities,'ROLE_REFERRE');
            	}
            	
            	$scope.deleteReferre = function(user,index){
            		$scope.users.push($scope.project.Users.splice(index,1)[0]);
            		$scope.project.usersIds = _.without($scope.project.usersIds,user.id);
            	}
            	
            	$scope.addReferre = function(user,index){
            		$scope.project.Users.push($scope.users.splice(index,1)[0]);
            		$scope.project.usersIds.push(user.id);
            	}
            	
            	$scope.ok = function () {
            		$uibModalInstance.close($scope.project);
            		scope.$destroy();
            	};
            	
            	$scope.cancel = function(){
            		scope.$destroy();
            		$scope.$dismiss();
            	}

            	initialize();
            }],
            resolve: {
              project: function () {
                return $scope.project;
              }
            }
          });
    		return modalInstance;
        }
        
        intialize(); 
    }
})();
