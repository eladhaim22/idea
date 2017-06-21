(function() {
    'use strict';

    angular
        .module('ideaApp')
        .controller('PeriodController', PeriodController);

    PeriodController.$inject = ['$scope','$state','periodService'];

    function PeriodController ($scope,$state,periodService) {
		var vm = this;
		
		vm.edit = $state.params.id ? true : false;
		
		function intialize(){
			if($state.params.id){
				periodService.get($state.params.id).then(function(period){
					vm.period = period;
					vm.period.startingDate = vm.period.startingDate.toDate();
					vm.period.presentionLimitDate = vm.period.presentionLimitDate.toDate();
					vm.period.endingDate = vm.period.endingDate.toDate();
				},function(error){
					
				});
			}
			else 
				vm.period = {};
		}
		
		vm.dateOptions = {
			formatYear: 'yy',
			maxDate: new Date(2020, 5, 22),
			minDate: new Date(),
			startingDay: 1
		};
	  
		vm.popupStart = {
			opened: false
		};
			
		vm.popupLimit = {
			opened: false
		};
		
		vm.popupEnd = {
			opened: false
		};
		
		vm.openStartingDate = function() {
		    vm.popupStart.opened = true;
	    };

	    vm.openPresentionLimitDate = function() {
	    	vm.popupLimit.opened = true;
	    };
	    
	    vm.openEndingDate = function() {
	    	vm.popupEnd.opened = true;
	    }
	    
	    vm.save = function(){
	    	if($scope.periodForm.$valid && vm.period.startingDate.getTime() < vm.period.presentionLimitDate.getTime() && vm.period.presentionLimitDate.getTime() < vm.period.endingDate.getTime()){
	    		periodService.save(vm.period).then(function(){
	    			$state.go('periods');
	    		},function(error){
	    			
	    		});
	    	}
	    }
	    	
    	intialize();
    }
})();
