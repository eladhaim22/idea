(function() {
    'use strict';

    angular
        .module('ideaApp')
        .controller('PeriodsController', PeriodsController);

    PeriodsController.$inject = ['$scope','$state','periodService'];

    function PeriodsController ($scope,$state,periodService) {
    	var vm = this;
    	
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
    	
    	intialize();
    }
})();
