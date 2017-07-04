(function() {
    'use strict';

    angular
        .module('ideaApp')
        .directive('showValidation', showValidation);

    function showValidation () {
        var directive = {
            restrict: 'A',
            require: 'form',
            link: linkFunc
        };

        return directive;

        function linkFunc (scope, element, attrs, formCtrl) {
        	var hasRenderd = false;
        	scope.$watch(function(){return element.find('input[ng-model][name],textarea[ng-model][name],select[ng-model][name]').length},function(newValue,oldValue){
	            if(newValue != oldValue || !hasRenderd){
	            	hasRenderd = true;
	        		element.find('.form-group').each(function() {
		                var $formGroup = angular.element(this);
		                var $inputs = $formGroup.find('input[ng-model][name],textarea[ng-model][name],select[ng-model][name]');
		                if ($inputs.length > 0) {
		                    $inputs.each(function() {
		                        var $input = angular.element(this);
		                        var inputName = $input.attr('name');
		                        scope.$watch(function() {
		                            return formCtrl[inputName].$invalid && formCtrl[inputName].$dirty;
		                        }, function(isInvalid) {
		                            $formGroup.toggleClass('has-error', isInvalid);
		                        });
		                    });
		                }
		            });
	            }
        	});
        }
    }
})();
