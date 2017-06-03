(function() {
    'use strict';

    angular
        .module('ideaApp')
        .controller('ProjectController', ProjectController);

    ProjectController.$inject = ['$scope', 'Principal', 'LoginService', '$state','projectService'];

    function ProjectController ($scope, Principal, LoginService, $state,projectService) {
    	var vm = this;
    	vm.project = {};
    	vm.project.team = [];

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
        
        vm.addPersonToProject = function(){
        	vm.project.team.push(angular.copy(vm.person));
        	vm.person = {};
        }
        
        vm.save = function(){
        	projectService.save(vm.project).then(function(){
        		
        	},function(error){
        		console.log(error);
        	});
        }
    }
})();
