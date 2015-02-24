appCtrl.controller('eventsCtrl', function($rootScope, $scope, $http, $ionicPopup, $state, $ionicPlatform, RestService, SearchCriteria,EventService,UserService) {	
  
	$scope.day = SearchCriteria.getDay();
	$scope.month = SearchCriteria.getMonth();
	$scope.year = SearchCriteria.getYear();

	$scope.loadEvents = function(){
		$scope.events = []
		RestService.getEvents($rootScope.SERVER_LOCATION).success(function(res){
			$scope.event_ids = res.extra;
			for (id in $scope.event_ids){
				var id_string = $scope.event_ids[id];
				EventService.getEvent($rootScope.SERVER_LOCATION,$scope.event_ids[id]).success(function(res){
					safeApply($scope,$rootScope,function(){

						$scope.events.push(res.extra);
						var num = $scope.events.length-1;

						$scope.events[num].time = EventService.getTimeDifference(res.extra.date);
						$scope.events[num].canJoin = EventService.isParticipant($rootScope.user_id,$scope.events[num]);
						$scope.events[num].isOwner = EventService.isOwner($rootScope.user_id,$scope.events[num]);

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
	};

/*	$ionicPlatform.ready(function(){
		$scope.loadEvents();
	});
*/
	//$scope.loadEvents();

	$scope.join = function(event_id){
		EventService.joinEvent($rootScope.SERVER_LOCATION,event_id,$rootScope.user_id).success(function(res){
			if (res.code == 0){
				$state.go("app.single",{eventId:event_id},{reload:true});
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
	};

	$scope.leave = function(event){
		EventService.leaveEvent($rootScope.SERVER_LOCATION,event.event_id,$rootScope.user_id).success(function(res){
			if (res.code == 0){
				safeApply($scope,$rootScope,function(){
					event.canJoin = true;
				})
				$scope.loadEvents();
			}else {
				$ionicPopup.alert({
					title: "Failed to leave.",
					template: "Error: "+res.message
				});				
			}
		}).error(function(error){
			$ionicPopup.alert({
				title: "Failed to leave.",
				template: "Error: "+error
			});
		});
	};
  
});

appCtrl.service('RestService', ['$http', function ($http) {
	
        var endpoint = 'event';

        this.getEvents = function (base_url) {

			return $http.get(base_url+endpoint);

        };
}]);