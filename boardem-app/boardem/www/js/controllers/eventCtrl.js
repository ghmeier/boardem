appCtrl.controller('eventCtrl', function($rootScope, $scope, $stateParams,EventService,UserService) {

  $scope.event = {};
  $scope.url = $rootScope.SERVER_LOCATION+"event/"+$stateParams.eventId;

  EventService.getEvent($rootScope.SERVER_LOCATION,$stateParams.eventId).success(function(res){
  	$scope.event = res.extra;
    $scope.date = EventService.getTimeDifference(res.extra.date);
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
  	})
  });
});


appCtrl.service('EventService', ['$http', function ($http) {
	
        var endpoint = 'event/';
				
				//Any other http request will be used as a service function
        this.getEvent = function (base_url,eventId,callback) {
			     return $http.get(base_url+endpoint+eventId);
        };

        this.joinEvent = function(base_url,event_id,user_id){
          return $http.post(base_url+endpoint+event_id+"/join",{user_id:user_id});
        }

        this.getTimeDifference = function(date){
          var cur = "20"+date.replace(/-/g,"/");

          var millis1 = Date.parse(cur);
          var millis2 = Date.now();

          var diff = Math.abs(millis2 - millis1);

          var days = Math.ceil(diff / (1000 * 3600 * 24));

          var hours = Math.ceil((diff %(1000 * 3600 * 24)) / (1000*3600));

          var min = Math.ceil((diff % (1000 * 3600) / (1000*60)))

          return {days:days,hours:hours};

        }

        this.getDistanceInKM = function(lat1,lng1,lat2,lng2){
          var R = 6371000; // metres
          var φ1 = this.toRad(lat1);
          var φ2 = this.toRad(lat2);
          var Δφ = this.toRad(lat2-lat1);
          var Δλ = this.toRad(lng2-lng1);

          var a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
                  Math.cos(φ1) * Math.cos(φ2) *
                  Math.sin(Δλ/2) * Math.sin(Δλ/2);
          var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

          var d = R * c;

          return (d/1000).toFixed(2);
        };

        this.toRad = function(val){
            /** Converts numeric degrees to radians */
            return val * Math.PI / 180;
        };
}]);