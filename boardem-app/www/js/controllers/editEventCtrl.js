appCtrl.controller('editEventCtrl', function($rootScope, $scope, $stateParams, $window,$ionicPopup, $ionicModal, $state, UserService, UtilService,EditEventService, CreateEventService,GameService,ExperienceService, EventService) {
  $scope.data = {games : '',query:'', name: ''};
	$scope.eventDay = CreateEventService.getDay();
	$scope.eventMonth = CreateEventService.getMonth();
	$scope.eventYear = CreateEventService.getYear();
	$scope.eventTime = CreateEventService.getTime();
	$scope.eventLocation = {};
	$scope.page = 0;
	$scope.shelf = [];
	$scope.eventGames = [];

	$ionicModal.fromTemplateUrl('location.html', {
	    scope: $scope,
	    animation: 'slide-in-up'
	  }).then(function(modal) {
	    $scope.modal = modal;
	  });

	$scope.loadEvent = function() {
		$scope.eventId = $stateParams.eventId;
		$scope.event = {};
    	$scope.location = [];
    	$scope.games = [];
		$scope.areGames = true;
		var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
						"Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

    EventService.getEvent($rootScope.SERVER_LOCATION,$stateParams.eventId).success(function(res){
    	$scope.event = res.extra;

			$scope.data.name = $scope.event.name;

			var stringDateTime = res.extra.date;
			var dateTimeArr = stringDateTime.split(" ");
			var dateArr = dateTimeArr[0].split("-");
			var timeArr = dateTimeArr[1].split(":");

			$scope.eventTime = timeArr[0];

			$scope.eventDay = parseInt(dateArr[2]);
			$scope.eventMonth = monthNames[parseInt(dateArr[1])-1];
			$scope.eventYear = parseInt(dateArr[0]);

			CreateEventService.setDate($scope.eventYear, parseInt(dateArr[1]), $scope.eventDay, $scope.eventTime);

			var longitude = res.extra.lng;
			var latitude = res.extra.lat;

	      navigator.geolocation.getCurrentPosition(function(pos){
	        $scope.pos = pos;
	        EventService.getLocationFromCoords(latitude,longitude,$scope.location);
	        safeApply($scope,$rootScope,function(){
	          $scope.event.distance = EventService.getDistanceInKM(latitude,longitude,pos.coords.latitude,pos.coords.longitude);

	        });
	      },function(error){
	        //nothing
	      });

			for (id in res.extra.games){
        GameService.getSingleGame($rootScope.SERVER_LOCATION,$scope.event.games[id]).success(function(gameRaw){
          if (gameRaw.code == 0 || gameRaw.code === "0"){
            $scope.areGames = true;
            $scope.eventGames.push(gameRaw.extra);
          }
        });
      }

			if(($scope.games).length == 0) $scope.areGames = false;

    });
	}

	$scope.changeEventDay = function(direction) {
			CreateEventService.changeEventDay(direction);
			$scope.eventDay = EditEventService.getDay();
	}

	$scope.changeEventMonth = function(direction) {
			CreateEventService.changeEventMonth(direction);
			$scope.eventMonth = EditEventService.getMonth();
	}

	$scope.changeEventYear = function(direction) {
			CreateEventService.changeEventYear(direction);
			$scope.eventYear = EditEventService.getYear();
	}

	$scope.changeEventTime = function(direction){
			CreateEventService.changeEventTime(direction);
			$scope.eventTime = EditEventService.getTime();
	}

	$scope.editEvent = function() {
			CreateEventService.getLocation(function(pos){
				var loc = {};
				var day = CreateEventService.getDay();
				var month = CreateEventService.getMonthInt();
				var year = CreateEventService.getYear();
				var time = CreateEventService.getTime();

				if ($scope.eventGames.length == 0){
					UtilService.popup("No Games","Be sure to select games for your event.")
				}

				if ($scope.data.name === ''){
					UtilService.popup("No Name","Name your event, we swear, it's fun!");
				}

				if ($scope.eventLocation.geometry){
					loc.lat = $scope.eventLocation.geometry.location.lat;
					loc.lng = $scope.eventLocation.geometry.location.lng;
				}else{
					loc.lat = $scope.event.lat;
					loc.lng = $scope.event.lng;
				}
				loc.owner = $rootScope.user_id;
				loc.games = $scope.eventGames;
				loc.name = $scope.data.name;
				loc.date = year+"-"+month+"-"+day+" "+time+":00:00";

				EditEventService.editEvent($rootScope.SERVER_LOCATION,$scope.eventId,loc).success(function(response){
						UtilService.checkBadges(response);

						var xp = 100;
						var xp = Math.round(Math.random() * (150 - 50) + 50);

						ExperienceService.addToUserXP($rootScope.SERVER_LOCATION, $rootScope.user_id, xp);
						$rootScope.xp_info = [];
						ExperienceService.updateUserXPInfo($rootScope.SERVER_LOCATION, $rootScope.user_id, $rootScope.xp_info);
			    		$state.go("app.single",{eventId:$scope.eventId});

				}).error(function(error){
					//console.log(error);
				});
			});
	}

	$scope.locationModal = function(){
		$scope.modal.show();
	}

	$scope.closeLocation = function(){
		$scope.modal.hide();
	}

	$scope.closeGame = function(){
		$scope.gModal.hide();
	}

	$scope.selectLocation = function(location){
		$scope.eventLocation = location;
		$scope.closeLocation();
	}

	$scope.selectGame = function(game){
		console.log(game);
		if (game.checked){
			console.log("wrod")
			game.checked = false;
			for (id in $scope.eventGames){
				if ($scope.eventGames[id] === game.name){
					$scope.eventGames.splice(id,1);
				}
			}
		}else{
			$scope.eventGames.push(game.name);
			game.checked = true;
		}
	}

	$scope.locationSearch = function(){
		$scope.locations = [];
		EditEventService.searchLocation($scope.data.query,$scope.locations);
	}

	$scope.gamesModal = function(){
		if ($rootScope.shelfGames.length == 0){
			$rootScope.shelfGames = [];
			UserService.getShelf($rootScope.SERVER_LOCATION,$rootScope.user_id,$rootScope.shelfGames);
		}
		  $ionicModal.fromTemplateUrl('games-modal.html', {
		    scope: $scope,
		    animation: 'slide-in-up'
		  }).then(function(modal) {
		    $scope.gModal = modal;
		    $scope.gModal.show();
		  });

	}

	$scope.getGames = function(){
		GameService.getAllGames($rootScope.SERVER_LOCATION,$scope.games,$scope.page,$rootScope.shelfGames);
		$scope.page++;
		$scope.$broadcast('scroll.infiniteScrollComplete');
	}
});

appCtrl.service('EditEventService', ['$http', function ($http) {

		this.editEvent = function(base_url,event_id,data){
			var url = base_url + "event/";
			return $http.put(url+event_id,data);
		}

}]);
