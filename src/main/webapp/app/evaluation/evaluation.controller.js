(function() {
    'use strict';

    angular
        .module('ideaApp')
        .controller('EvaluationController', EvaluationController);

    EvaluationController.$inject = ['$scope', '$state','templateService','evaluationService'];

    function EvaluationController ($scope,$state,templateService,evaluationService) {
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
    	
    	 vm.calculateAnswerIndex = function(id){
     		return _.indexOf(vm.evaluation.answers,_.findWhere(vm.evaluation.answers,{questionId:id}));
     	}
        
        vm.save = function(){
        	if(!_.some(vm.evaluation.answers, function(answer) {
        	    return answer.questionAnswer == undefined;
        	})){
	        	vm.evaluation.projectId = $state.params.projectId;
	        	evaluationService.save(vm.evaluation).then(function(){
	        	$state.go('projects');
	        	},function(error){
	        		console.log(error);
	        	});
        	}
        	else{
        		vm.raiseError = true;
        	}
        }
        
        intialize(); 
    }
})();
