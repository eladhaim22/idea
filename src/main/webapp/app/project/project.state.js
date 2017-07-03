(function() {
    'use strict';

    angular
        .module('ideaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider','$sceProvider'];

    function stateConfig($stateProvider,$sceProvider) {
        $stateProvider.state('project', {
            parent: 'app',
            url: '/project/:id',
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
        $sceProvider.enabled(false);
    }
})();
