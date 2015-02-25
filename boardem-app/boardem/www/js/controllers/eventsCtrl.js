appCtrl.controller('eventsCtrl', function($rootScope, $scope, $http, $ionicPopup, $state, $ionicPlatform, RestService, SearchCriteria,EventService,UserService) {	
  
	$scope.day = SearchCriteria.getDay();
	$scope.month = SearchCriteria.getMonth();
	$scope.year = SearchCriteria.getYear();
	
	$scope.loadEvents = function(){
		$scope.events = EventService.loadEvents();
	};

/*$ionicPlatform.ready(function(){
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