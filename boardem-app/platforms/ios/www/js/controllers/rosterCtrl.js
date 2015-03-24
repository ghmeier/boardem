appCtrl.controller('rosterCtrl',function($rootScope,$http,$scope,EventService){
    $scope.roster = [];

    $scope.getRoster = function(){
        EventService.getRosterDetail($rootScope.SERVER_LOCATION,$rootScope.user_id,$scope.roster);
    }

});