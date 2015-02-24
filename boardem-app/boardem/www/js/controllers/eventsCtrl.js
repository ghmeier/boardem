appCtrl.controller('eventsCtrl', function($rootScope, $scope, $http, RestService, SearchCriteria) {	
  
	$scope.day = SearchCriteria.getDay();
	$scope.month = SearchCriteria.getMonth();
	$scope.year = SearchCriteria.getYear();
	$rootScope.events = RestService.getEvents(($scope.month + " " + $scope.day + " " + $scope.year));
  
});

appCtrl.service('RestService', ['$http', function ($http) {
	
        var urlBase = '/events';
				
				//Any other http request will be used as a service function
        this.getEvents = function (dateString) {
					//We dont need to return anything, one we get back the json data from
					//the server, we have have to parse it and set it the related scope variable
					return [
							{ title: 'John\'s game night.', id: 1, date: dateString },
							{ title: 'Mandy has some games', id: 2, date: dateString },
					];
						//It will look like this
            //return $http.get(urlBase);
        };
}]);