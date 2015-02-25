appCtrl.controller('eventCtrl', function($rootScope, $scope, $stateParams, $state, $ionicPopup, EventService,UserService) {

  $scope.loadEvent = function(){
    $scope.event = {};

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

  $scope.join = function(event_id){
      EventService.joinEvent($rootScope.SERVER_LOCATION,event_id,$rootScope.user_id).success(function(res){
      if (res.code == 0){
        $ionicPopup.alert({
          title: "Success!",
          template: "You joined the event!"
        }); 
        $scope.loadEvent();
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
        $ionicPopup.alert({
          title: "Success!",
          template: "You left the event!"
        }); 
        safeApply($scope,$rootScope,function(){
          event.canJoin = true;
        })
        $scope.loadEvent();
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


