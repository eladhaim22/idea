(function() {
    'use strict';

    angular.module('ideaApp')
    .config(stateConfig);//.config(translateConfig);

    stateConfig.$inject = ['$stateProvider'];
    /*translateConfig.$inject = ['$translateProvider'];
    
    function translateConfig($translateProvider){
    	$translateProvider.preferredLanguage('es');
    }*/

    function stateConfig($stateProvider) {
        $stateProvider.state('app', {
            abstract: true,
            views: {
                'navbar@': {
                    templateUrl: 'app/layouts/navbar/navbar.html',
                    controller: 'NavbarController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                authorize: ['Auth',
                    function (Auth) {
                        return Auth.authorize();
                    }
                ]
            }
        });
    }
})();

