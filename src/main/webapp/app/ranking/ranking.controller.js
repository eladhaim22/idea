(function() {
    'use strict';

    angular
        .module('ideaApp')
        .controller('RankingController', RankingController);

    RankingController.$inject = ['$scope', '$state','rankingService'];

    function RankingController ($scope,$state,rankingService) {
    	var vm = this;
    	
    	function intialize(){
    		rankingService.getAll().then(function(ranking){
    			vm.ranking = _.sortBy(ranking,'average');
    		},function(error){
    			
    		});
    	}
        
        intialize(); 
    }
})();
