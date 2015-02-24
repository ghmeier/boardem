appCtrl.controller('eventsCtrl', function($rootScope, $scope, $http, $ionicPopup, $window, $state, RestService, SearchCriteria,EventService,UserService) {	
  
	$scope.day = SearchCriteria.getDay();
	$scope.month = SearchCriteria.getMonth();
	$scope.year = SearchCriteria.getYear();
	$scope.events = [];

	RestService.getEvents($rootScope.SERVER_LOCATION).success(function(res){
		$scope.event_ids = res.extra;
		for (id in $scope.event_ids){
			var id_string = $scope.event_ids[id];
			EventService.getEvent($rootScope.SERVER_LOCATION,$scope.event_ids[id]).success(function(res){
				safeApply($scope,$rootScope,function(){

					$scope.events.push(res.extra);
					var num = $scope.events.length-1;
					$scope.events[num].time = EventService.getTimeDifference(res.extra.date);
					UserService.getUser($rootScope.SERVER_LOCATION,$scope.events[num].owner).success(function(response){
						$scope.events[num].owner_profile = response.extra;
					});
				});
			});
		}
	}).error(function(error){
		$ionicPopup.alert({
			title: "Failed to Retrieve events.",
			template: "Error: "+error
		});
	});

	$scope.join = function(event_id){
		console.log($window.localStorage['id']);
		EventService.joinEvent($rootScope.SERVER_LOCATION,event_id,$window.localStorage['id']).success(function(res){
			if (res.code == 0){
				$state.go("app.single",{eventId:event_id});
			}else {
				$ionicPopup.alert({
					title: "Failed to join.",
					template: "Error: "+res.message
				});				
			}
		}).error(function(error){
			$ionicPopup.alert({
				title: "Failed to join.",
				template: "Error: "+error
			});
		});
	}
  
});

appCtrl.service('RestService', ['$http', function ($http) {
	
        var endpoint = 'event';

        this.getEvents = function (base_url) {

			return $http.get(base_url+endpoint);

        };
}]);