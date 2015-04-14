appCtrl.controller('rosterCtrl',function($rootScope,$http,$scope,EventService,UserService){
		$scope.noEvents = false;

    $scope.getRoster = function(){
        $rootScope.roster = [];
        $rootScope.completed = [];
        EventService.getRosterDetail($rootScope.SERVER_LOCATION,$rootScope.user_id,$rootScope.roster);
				//if(($scope.roster).length == 0) $scope.noEvents = true;
        EventService.getCompletedEvents($rootScope.SERVER_LOCATION,$rootScope.user_id,$rootScope.completed);
    }

});