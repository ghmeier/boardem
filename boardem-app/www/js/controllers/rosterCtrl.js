appCtrl.controller('rosterCtrl',function($rootScope,$http,$scope,EventService){
    $scope.roster = [];
		$scope.noEvents = false;

    $scope.getRoster = function(){
        EventService.getRosterDetail($rootScope.SERVER_LOCATION,$rootScope.user_id,$scope.roster);
				//if(($scope.roster).length == 0) $scope.noEvents = true;
    }

});