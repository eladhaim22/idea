angular.module('ideaApp')
   .directive('evaluationChart',['evaluationService','$sce', function(evaluationService,$sce) {
	  return {
	    restrict: 'A',
	    scope: '=',
	    template: '<span ng-repeat="r in referres">' +
	    				'<button style="margin-right: 4px;padding: 7px 5px;" tooltip-placement="top" uib-tooltip-html="getNameAndDate(r)" type="button" class="btn"' +
	    				'ng-class="{\'btn-default\':!r.evaluation,\'btn-success\':r.evaluation}"></button>' +
	    		  '</span>',
	    link: function(scope) {
	    	evaluationService.get("ByProjectId/" + scope.project.id).then(function(evaluations){
		    	if(_.filter(scope.project.states,function(state){return state.active})[0].status == "PreSelected"){
		    		scope.referres = _.filter(scope.project.Users,function(user){return _.contains(user.authorities,"ROLE_REFERRE")});
		    		_.forEach(scope.referres,function(referre){
		    			referre.evaluation = _.filter(evaluations,function(e){return e.createdBy == referre.login}).length > 0 ?
		    				_.filter(evaluations,function(e){return e.createdBy == referre.login})[0] : undefined;
		    		});
		    	}
	    	},function(error){
	    		
	    	});
	 
	    	scope.getNameAndDate = function(r){
	    		var date = r.evaluation ? moment(r.evaluation.createdDate).format("DD/MM/YYYY") : "";
	    		return  $sce.trustAsHtml(r.firstName + ' ' + r.lastName + '</br>' + date);
	    	}
	    }
	  };
	}]);