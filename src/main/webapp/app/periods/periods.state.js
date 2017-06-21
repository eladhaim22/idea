(function() {
    'use strict';

    angular
        .module('ideaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('periods', {
            parent: 'app',
            url: '/periods',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/periods/periods.html',
                    controller: 'PeriodsController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
