(function() {
    'use strict';

    angular
        .module('ideaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('project', {
            parent: 'app',
            url: '/project',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/project/project.html',
                    controller: 'ProjectController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
