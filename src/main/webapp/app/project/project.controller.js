(function() {
    'use strict';

    angular
        .module('ideaApp')
        .controller('ProjectController', ProjectController).controller('AddPersonModelController',AddPersonModelController).controller('ReferreModelController',ReferreModelController);

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
    			openReferreModal().result.then(function (project) {
		        	projectService.changeState(project.id,project.usersIds,_.indexOf(states,state),null).then(function(data){
		        		$state.go('projects');
		        	},function(error){
		        		console.log('error');
		        	});
		        }, function () {
		        	console.log('Modal dismissed at: ' + new Date());
		        });
    		}
    		else if(state == 'Rejected'){
    			rejectModal().result.then(function(comment){
    				projectService.changeState(vm.project.id,null,_.indexOf(states,state),comment).then(function(data){
    	        		$state.go('projects');
    	        	},function(error){
    	        		console.log('error');
    	        	});
    			},function(error){
    				console.log('');
    			});
    		}
    	}
    	
    	vm.getActiveStatus = function(){
    		if(vm.project.states){
    			return _.filter(vm.project.states,function(state){return state.active == 1})[0].status;
    		}
    	}
    	
    	vm.addTeam = function(){
    		OpenAddPersonModal().result.then(function(team){
    			vm.project.team = team;
    		},function(error){
    			console.log(error);
    		});
    	}
    	
    	vm.getAnswerNode = function(questionId){
    		return _.filter(vm.project.answers,function(answer){
    			return answer.questionId = questionId;
    		})[0].questionAnswer;
    	}
    	
        vm.deletePersonFromProject = function(index){
        	vm.project.team.splice(index);
        }
        
        vm.save = function(){
        	if($scope.form.$valid && vm.project.team.length > 0){
		        	projectService.save(vm.project).then(function(){
		        		$state.go('projects');
		        	},function(error){
		        		console.log(error);
		        	});
        	}
        	else
        		if(!$scope.form.$valid){
		    		 angular.forEach($scope.form.$error, function(controls, errorName) {
		    		        angular.forEach(controls, function(control) {
		    		            if(control.$name != 'firstName' && control.$name != 'lastName' && control.$name != 'dni'
		    		            	&& control.$name != 'fileNumber' && control.$name != 'career' && control.$name != 'stage'
		    		            		&& control.$name != 'age' && control.$name != 'phoneNumber' && control.$name != 'email'
		    		            			&& control.$name != 'Type'){
			            			control.$setDirty();
		    		            }
		    		        });
		    		    });
        		}
        		if(!vm.project.team.length > 0){
        			vm.teamError = true;
        		}
        		else{
        			vm.teamError = false;
        		}
        }
        
        vm.navigateToEvaluation = function(){
        	$state.go('evaluation',{projectId:$state.params.id});
        }
        
        
        function OpenAddPersonModal(){
        	var scope = $scope.$new(true);
        	scope.team = vm.project.team ? angular.copy(vm.project.team) : [];
    		var modalInstance = $uibModal.open({
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            appendTo: $('.project').eq(0),
            scope:scope,
            backdrop: 'static',
            size:'lg',
            templateUrl: 'app/components/modal/projectAddPerson.html',
            controller: 'AddPersonModelController',
            resolve: {
              team: function () {
            	  return $scope.team;
              }
            }
          });
    		return modalInstance;
        }
        
        function rejectModal (){
        	var scope = $scope.$new(true);
        	scope.project = angular.copy(vm.project);
    		var modalInstance = $uibModal.open({
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            scope:scope,
            backdrop: 'static',
            size:'lg',
            templateUrl: 'app/components/modal/projectRejectModal.html',
            controller: ['$scope','$uibModalInstance', function($scope,$uibModalInstance){      
            	$scope.ok = function () {
            		$uibModalInstance.close($scope.comment);
            	}
            	
            	$scope.cancel = function(){
            		$scope.$dismiss();
            	}
            }],
            resolve: {
              comment: function () {
            	  return $scope.comment;
              }
            }
          });
    		return modalInstance;
        }
        
        
        function openReferreModal (){
        	var scope = $scope.$new(true);
        	scope.project = angular.copy(vm.project);
    		var modalInstance = $uibModal.open({
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            scope:scope,
            backdrop: 'static',
            size:'lg',
            templateUrl: 'app/components/modal/projectReferreModal.html',
            controller: 'ReferreModelController',
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

    //Add person modal
    
    AddPersonModelController.$inject = ['$scope','$uibModalInstance'];
	function AddPersonModelController($scope,$uibModalInstance){            	
		$scope.stages = ['1er año','2do año','3er año','4to año','5to año','Graduado','Post Grado'];
		$scope.person = {};
		$scope.addPersonToProject = function(){
	    	if(personIsValid()){
	        	$scope.team.push($scope.person);
	        	$scope.person = {};
	        	$scope.personType = undefined;
	        	setPersonInputsPristine();
	    	}
	    }
	    
	    function setPersonInputsPristine(){
	    	$scope.personForm.Type.$setPristine();
	    	$scope.personForm.firstName.$setPristine();
			$scope.personForm.lastName.$setPristine();
			$scope.personForm.dni.$setPristine();
			$scope.personForm.age.$setPristine();
			$scope.personForm.phoneNumber.$setPristine();
			$scope.personForm.email.$setPristine();
	    }
	    
	    function personIsValid(){
	    	if($scope.person.type){
	    		var invalid = false;
	    		if(!$scope.personForm.firstName.$valid || !$scope.personForm.lastName.$valid || !$scope.personForm.dni.$valid ||
	    				!$scope.personForm.email.$valid || !$scope.personForm.phoneNumber.$valid || !$scope.personForm.age.$valid){
	    			$scope.personForm.firstName.$setDirty();
	    			$scope.personForm.lastName.$setDirty();
	    			$scope.personForm.dni.$setDirty();
	    			$scope.personForm.age.$setDirty();
	    			$scope.personForm.phoneNumber.$setDirty();
	    			$scope.personForm.email.$setDirty();
	    			invalid = true;
	    		}
	    		if(($scope.person.type == 'personUade') && (!$scope.personForm.fileNumber.$valid || !$scope.personForm.career.$valid ||
	    				$scope.person.stage == undefined)){
	    			$scope.personForm.fileNumber.$setDirty();
	    			$scope.personForm.career.$setDirty();
	    			$scope.personForm.stage.$setDirty();
	    			return false;
	    		}
	    		return !invalid ? true : false; 
	    	}
	    	else {
	    		$scope.personForm.Type.$setDirty();
	    		return false;
	    	}
	    }
	    
	    $scope.removePerson = function(index){
	    	$scope.team.splice(index,1);
	    }
	    
	    $scope.ok = function () {
	    	if($scope.team.length > 0){
	    		$uibModalInstance.close($scope.team);
			}
			else
				$scope.raiseError = true;
		};
		
		$scope.cancel = function(){
			$scope.$dismiss();
		}
		
	}
	
	//Add referre modal
	
	ReferreModelController.$inject = ['$scope','User','$uibModalInstance'];
	function ReferreModelController($scope,User,$uibModalInstance){            	
    	function initialize(){
    		User.query({},function(data){
    			$scope.users = _.filter(data, function(user){
            	    return _.find($scope.$parent.project.Users, function(userInProject){
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
    		if(_.some($scope.project.Users,function(user){return _.contains(user.authorities,'ROLE_REFERRE')})){
        		$uibModalInstance.close($scope.project);
    		}
    		else
    			$scope.raiseError = true;
    	};
    	
    	$scope.cancel = function(){
    		$scope.$dismiss();
    	}

    	initialize();
    }
    
})();
