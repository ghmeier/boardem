appCtrl.controller('eventsCtrl', function($rootScope, $scope, $http) {

  $scope.events = RestFactory.getEvents();
  
});

appCtrl.service('RestFactory', ['$http', function ($rootScope, $http) {
	
        var urlBase = '/events';
				
				//Any other http request will be used as a service function
        this.getEvents = function () {
					//We dont need to return anything, one we get back the json data from
					//the server, we have have to parse it and set it the related scope variable
					return [
							{ title: 'John\'s game night.', id: 1 },
							{ title: 'Mandy has some games', id: 2},
					];
						//It will look like this
            //return $http.get(urlBase);
        };
}]);