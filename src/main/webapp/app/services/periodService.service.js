(function () {
	var module = angular.module('ideaApp');

	module.factory('periodService', ['entityServiceFactory','restService', function (entityServiceFactory,restService) {
		var periodService = entityServiceFactory.create('period');
		

		periodService.fill = entityServiceFactory.buildFill(null, null,
		function (period) {
			period.startingDate = new moment(period.startingDate);
			period.presentionLimitDate = new moment(period.presentionLimitDate);
			period.endingDate = new moment(period.endingDate);
		});
		

		
		return periodService;
	}]);
})();