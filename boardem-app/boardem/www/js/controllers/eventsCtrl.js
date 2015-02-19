appCtrl.controller('eventsCtrl', function($rootScope, $scope, $http, RestFactory) {

  $scope.events = RestFactory.getEvents();
  
});

appCtrl.service('RestFactory', ['$http', function ($rootScope, $http) {
	
        var urlBase = '/events';
				
				//Any other http request will be used as a service function
        this.getEvents = function () {
					
					return [
							{ title: 'John\'s game night.', id: 1 },
							{ title: 'Mandy has some games', id: 2 },
					];
						//It will look like this
            //return $http.get(urlBase);
        };
}]);