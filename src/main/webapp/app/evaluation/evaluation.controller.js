(function() {
    'use strict';

    angular
        .module('ideaApp')
        .controller('EvaluationController', EvaluationController);

    EvaluationController.$inject = ['$scope', '$state','projectService','User','$uibModal','templateService','$q'];

    function EvaluationController ($scope,$state,projectService,User,$uibModal,templateService,$q) {
    	var vm = this;
    	vm.edit = $state.params.id ? true : false;
    	vm.evaluation = {}
    	vm.evaluation.answers = [];
    	
    	function intialize(){
    		templateService.getByName('Formulario_Evaluacion').then(function(template){
    			vm.template = template;
    			vm.sectionAndSubSection = [];
    			angular.forEach(_.toArray(_.groupBy(template.questions,'section')),function(value){
    				vm.sectionAndSubSection.push(_.toArray(_.groupBy(value,'subsection')));
    			});
    			if(!$state.params.id){
	    			_.each(template.questions,function(question){
						vm.evaluation.answers.push({id:null,questionId : question.id,questionAnswer:undefined})
					});
    			}
    		},function(error){
    			
    		});
    	}
    	
    	vm.setAnswer = function(questionId,value){
       		var answer =_.findWhere(vm.evaluation.answers,{questionId:questionId});
			answer.questionAnswer = value;
    	}
    	
    	vm.changeState = function (){
    		openModal().result.then(function (project) {
	        	projectService.changeState(project.id,project.usersIds).then(function(data){
	        		$state.go('projects');
	        	},function(error){
	        		console.log('error');
	        	});
	        }, function () {
	        	console.log('Modal dismissed at: ' + new Date());
	        });
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
        		
        	},function(error){
        		console.log(error);
        	});
        }
        
        vm.openAssignModal = function(){	
        	openModal().result.then(function (project) {
	        	projectService.assignReferreToProject(project.id,project.usersIds).then(function(data){
	        		console.log('El proyecto se guardo');
	        		vm.project = project;
	        	},function(error){
	        		console.log('error');
	        	});
	        }, function () {
	        	console.log('Modal dismissed at: ' + new Date());
	        });
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
        
        vm.evalauateProject = function(){
        	$state.go('project/' + $state.params.id + '/evaluate');
        }
        
        intialize(); 
    }
})();