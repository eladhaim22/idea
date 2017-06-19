(function() {
    'use strict';

    angular
        .module('ideaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('ranking', {
            parent: 'app',
            url: '/ranking',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/ranking/ranking.html',
                    controller: 'RankingController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
