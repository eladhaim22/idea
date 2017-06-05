(function() {
    'use strict';

    angular
        .module('ideaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('projects', {
            parent: 'app',
            url: '/projects',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/projects/projects.html',
                    controller: 'ProjectsController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
