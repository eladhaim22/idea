(function() {
    'use strict';

    angular
        .module('ideaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('evaluation', {
            parent: 'app',
            url: '/project/:projectId/evaluation',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/evaluation/evaluation.html',
                    controller: 'EvaluationController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
