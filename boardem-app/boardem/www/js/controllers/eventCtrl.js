appCtrl.controller('eventCtrl', function($rootScope, $scope, $stateParams,EventService,UserService) {

  $scope.event = {};
  $scope.url = $rootScope.SERVER_LOCATION+"event/"+$stateParams.eventId;

  EventService.getEvent($scope.url).success(function(res){
  	$scope.event = res.extra;
  	console.log(res.extra);
  	UserService.getUser($rootScope.SERVER_LOCATION + "users/"+$scope.event.owner)
  	.success(function(data){
  		$scope.owner = data.extra;
  	})
  });
});


appCtrl.service('EventService', ['$http', function ($http) {
	
        var urlBase = '/events';
				
				//Any other http request will be used as a service function
        this.getEvent = function (url,callback) {
			return $http.get(url);
        };
}]);