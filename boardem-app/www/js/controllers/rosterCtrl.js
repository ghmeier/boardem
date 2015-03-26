appCtrl.controller('rosterCtrl',function($rootScope,$http,$scope,EventService){
		$scope.noEvents = false;

    $scope.getRoster = function(){
        $rootScope.roster = [];
        EventService.getRosterDetail($rootScope.SERVER_LOCATION,$rootScope.user_id,$rootScope.roster);
				//if(($scope.roster).length == 0) $scope.noEvents = true;
    }

});