appCtrl.controller('completeEventCtrl', function($rootScope, $scope, $stateParams, $state, UtilService, EventService,UserService,GameService) {

  $scope.loadEvent = function(){
    $scope.event = {};
    $scope.location = [];
    $scope.games = [];
        $scope.areGames = true;
        $scope.eventId = $stateParams.eventId;
    $scope.pics = {};
        var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
                        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

    EventService.getCompletedEvent($rootScope.SERVER_LOCATION,$stateParams.eventId).success(function(res){
        $scope.event = res.extra;

      $scope.date = EventService.getTimeDifference(res.extra.date);
      $scope.event.canJoin = EventService.isParticipant($rootScope.user_id,$scope.event);
      $scope.event.isOwner = EventService.isOwner($rootScope.user_id,$scope.event);

      EventService.getEventComments($rootScope.SERVER_LOCATION,$scope.eventId,$scope.event);
            var dateSplit = (res.extra.date).split(" ");
            var dateDate = dateSplit[0].split("-");
            var dateTime = dateSplit[1].split(":");
            $scope.event.eventDay = dateDate[2] + " " + monthNames[(dateDate[1]-1)];
            if(dateTime[0] > 12) $scope.event.eventTime = (dateTime[0]-12) + ":" + dateTime[1] + "pm";
            else $scope.event.eventTime = dateTime[0] + ":" + dateTime[1] + "am";

      navigator.geolocation.getCurrentPosition(function(pos){
        $scope.pos = pos;
        EventService.getLocationFromCoords($scope.event.lat,$scope.event.lng,$scope.location);
        safeApply($scope,$rootScope,function(){
          $scope.event.distance = EventService.getDistanceInKM($scope.event.lat,$scope.event.lng,pos.coords.latitude,pos.coords.longitude);

        });
      },function(error){
        //nothing
      });
      for (id in $scope.event.games){
        GameService.getSingleGame($rootScope.SERVER_LOCATION,$scope.event.games[id]).success(function(gameRaw){
          if (gameRaw.code == 0 || gameRaw.code === "0"){
            $scope.areGames = true;
            $scope.games.push(gameRaw.extra);
          }
        });
      }

            if(($scope.games).length == 0) $scope.areGames = false;


        UserService.getUser($rootScope.SERVER_LOCATION ,$scope.event.owner)
        .success(function(data){
            $scope.owner = data.extra;
        $scope.pics[$scope.owner.facebook_id] = data.extra.picture_url;
        });
      $scope.event.participant_profile = [];
      for (id in $scope.event.participants){
        UserService.getUser($rootScope.SERVER_LOCATION ,$scope.event.participants[id])
        .success(function(data){
            $scope.event.participant_profile.push(data.extra);
            $scope.pics[data.extra.facebook_id] = data.extra.picture_url;
        });
      }
    });
  };

});


