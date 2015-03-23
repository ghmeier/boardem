appCtrl.controller("shelfCtrl",function($window,$rootScope,$scope,$state,$http,$firebase,$firebaseAuth,UtilService){

	$scope.loadShelf = function(){
    $scope.games = {};

    EventService.getEvent($rootScope.SERVER_LOCATION,$stateParams.eventId).success(function(res){
    	$scope.event = res.extra;

      $scope.date = EventService.getTimeDifference(res.extra.date);
      $scope.event.canJoin = EventService.isParticipant($rootScope.user_id,$scope.event);
      $scope.event.isOwner = EventService.isOwner($rootScope.user_id,$scope.event);

      navigator.geolocation.getCurrentPosition(function(pos){
        safeApply($scope,$rootScope,function(){
          $scope.event.distance = EventService.getDistanceInKM($scope.event.lat,$scope.event.lng,pos.coords.latitude,pos.coords.longitude); 
        });     
      },function(error){
        //nothing
      });

    	UserService.getUser($rootScope.SERVER_LOCATION ,$scope.event.owner)
    	.success(function(data){
    		$scope.owner = data.extra;
    	});
      $scope.event.participant_profile = [];
      for (id in $scope.event.participants){
        UserService.getUser($rootScope.SERVER_LOCATION ,$scope.event.participants[id])
        .success(function(data){
          safeApply($scope,$rootScope,function(){
            $scope.event.participant_profile.push(data.extra);
          });
        });      
      }
    });
  };

});