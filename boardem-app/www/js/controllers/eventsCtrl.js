appCtrl.controller('eventsCtrl', function($rootScope, $scope, $http, $state, $ionicPlatform, UtilService, RestService, SearchCriteria,EventService,UserService) {

	$scope.day = SearchCriteria.getDay();
	$scope.month = SearchCriteria.getMonth();
	$scope.year = SearchCriteria.getYear();

	$scope.loadEvents = function(){
		$rootScope.events = EventService.loadEvents();
	};

	$scope.join = function(event_id){
		EventService.joinEvent($rootScope.SERVER_LOCATION,event_id,$rootScope.user_id).success(function(res){
			if (res.code == 0){
				UtilService.popup("Joined","Joined the event");
				$scope.loadEvents();
				$rootScope.roster = [];
				EventService.getRosterDetail($rootScope.SERVER_LOCATION,$rootScope.user_id,$rootScope.roster);
				$state.go("app.single",{eventId:event_id},{reload:true});
			}else {
				UtilService.popup("Failed to join.","Error "+res.message);
			}
		}).error(function(error){
			UtilService.popup("Failed to join.","Error: "+error);
		});
	};

	$scope.leave = function(event){
		EventService.leaveEvent($rootScope.SERVER_LOCATION,event.event_id,$rootScope.user_id).success(function(res){
			if (res.code == 0){
				safeApply($scope,$rootScope,function(){
					event.canJoin = true;
					$rootScope.roster = [];
					EventService.getRosterDetail($rootScope.SERVER_LOCATION,$rootScope.user_id,$rootScope.roster);
					$scope.loadEvents();
				});
			}else {
				UtilService.popup("Failed to leave.","Error: "+res.message);
			}
		}).error(function(error){
			UtilService.popup("Failed to leave.","Error: "+error);
		});
	};

});