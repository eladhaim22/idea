(function() {
    'use strict';

    angular
        .module('ideaApp')
        .controller('PeriodsController', PeriodsController);

    PeriodsController.$inject = ['$scope','$state','periodService'];

    function PeriodsController ($scope,$state,periodService) {
    	var vm = this;
    	vm.error = undefined;
    	
    	function intialize(){
    		periodService.getAll().then(function(periods){
    			vm.periods = _.sortBy(periods,'startingDate');
    		},function(error){
    			
    		});
    	}
    	
    	vm.navigateToPeriod = function(id){
    		$state.go('period',{id:id});
    	}
    	
    	vm.newPeriod = function(){
    		$state.go('period');
    	}
    	
    	vm.deactivatePeriod = function(id){
    		periodService.get("Deactivate/" + id).then(function(){
    			intialize();
    		},function(error){
    			
    		});
    	}
    	
    	vm.activatePeriod = function(id){
    		periodService.get("Activate/" + id).then(function(){
    			intialize();
    			vm.error = undefined;
    		},function(error){
    			vm.error = error;
    		});
    		
    	}
    	
    	intialize();
    }
})();
