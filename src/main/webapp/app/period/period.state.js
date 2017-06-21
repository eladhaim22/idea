(function() {
    'use strict';

    angular
        .module('ideaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('period', {
            parent: 'app',
            url: '/period/:id',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/period/period.html',
                    controller: 'PeriodController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
