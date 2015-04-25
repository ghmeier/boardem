appCtrl.controller('editEventCtrl', function($rootScope, $scope, $stateParams, $window,$ionicPopup, $ionicModal, $state, UserService, UtilService, EditEventService,GameService,ExperienceService, EventService) {
  $scope.data = {games : '',query:'', name: ''};
	$scope.eventDay = EditEventService.getDay();
	$scope.eventMonth = EditEventService.getMonth();
	$scope.eventYear = EditEventService.getYear();
	$scope.eventTime = EditEventService.getTime();
	$scope.eventLocation = {};
	$scope.locations = [];
	//$scope.games = [];
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
      //$scope.date = EventService.getTimeDifference(res.extra.date);
		
			var stringDateTime = res.extra.date;
			//console.log("---" + JSON.stringify(res.extra));
			var dateTimeArr = stringDateTime.split(" ");
			var dateArr = dateTimeArr[0].split("-");
			var timeArr = dateTimeArr[1].split(":");
			
			$scope.eventTime = timeArr[0];
			
			$scope.eventDay = parseInt(dateArr[2]);
			$scope.eventMonth = monthNames[parseInt(dateArr[1])-1];
			$scope.eventYear = parseInt(dateArr[0]);
			
			EditEventService.setDate($scope.eventYear, parseInt(dateArr[1]), $scope.eventDay, $scope.eventTime);
		
			var longitude = res.extra.lng;
			var latitude = res.extra.lat;
			
			//EditEventService.getSearchLocation(latitude,longitude,$scope.eventLocation);
			
			console.log("--lksd--" + JSON.stringify($scope.eventLocation));
			
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
			EditEventService.changeEventDay(direction);
			$scope.eventDay = EditEventService.getDay();
	}

	$scope.changeEventMonth = function(direction) {
			EditEventService.changeEventMonth(direction);
			$scope.eventMonth = EditEventService.getMonth();
	}

	$scope.changeEventYear = function(direction) {
			EditEventService.changeEventYear(direction);
			$scope.eventYear = EditEventService.getYear();
	}

	$scope.changeEventTime = function(direction){
			EditEventService.changeEventTime(direction);
			$scope.eventTime = EditEventService.getTime();
	}

	$scope.editEvent = function() {
			EditEventService.getLocation(function(pos){
				var loc = {};
				var day = EditEventService.getDay();
				var month = EditEventService.getMonthInt();
				var year = EditEventService.getYear();
				var time = EditEventService.getTime();

				if (!$scope.eventLocation.geometry){
					UtilService.popup("No Location","Please select a location to host your event.");
					return;
				}

				if ($scope.eventGames.length == 0){
					UtilService.popup("No Games","Be sure to select games for your event.")
				}

				if ($scope.data.name === ''){
					UtilService.popup("No Name","Name your event, we swear, it's fun!");
				}

				loc.lat = $scope.eventLocation.geometry.location.lat;
				loc.lng = $scope.eventLocation.geometry.location.lng;
				loc.owner = $rootScope.user_id;
				loc.games = $scope.eventGames;
				loc.name = $scope.data.name;
				loc.date = year+"-"+month+"-"+day+" "+time+":00:00";

				EditEventService.editEvent($rootScope.SERVER_LOCATION,loc).success(function(response){
						UtilService.checkBadges(response);

						var xp = 100;
						var xp = Math.round(Math.random() * (150 - 50) + 50);

						ExperienceService.addToUserXP($rootScope.SERVER_LOCATION, $rootScope.user_id, xp);
						$rootScope.xp_info = [];
						ExperienceService.updateUserXPInfo($rootScope.SERVER_LOCATION, $rootScope.user_id, $rootScope.xp_info);
			    		$state.go("app.single",{eventId:response.extra});

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
		if (game.checked){
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

		var dateObject = new Date();
		var daysArray = ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'];
		var monthsArray = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
		//Get date in numbers
		var month = dateObject.getMonth();
		var day = dateObject.getDate();
		var year = dateObject.getFullYear();
		var time = dateObject.getHours();
		var currentYear = dateObject.getFullYear();
		//Get date in string format
		var monthString = monthsArray[dateObject.getMonth()];

		this.changeEventDay = function (direction) {
								
				if(direction == 1 || direction == "1") {
						day++;
						if(day==32)day = 1;
				} else {
						day--;
						if(day==0)day = 31;
				}

		};

		this.changeEventMonth = function (direction) {

				if(direction == 1 || direction == "1") {
						month++;
						if(month==12)month = 0;
				} else {
						month--;
						if(month==-1)month = 11;
				}
				monthString = monthsArray[month];
		};

		this.changeEventYear = function (direction) {

				if(direction == 1 || direction == "1") {
						if(year!=currentYear+5) year++;
				} else {
						if(year!=currentYear) year--;

				}
		};

		this.changeEventTime = function(direction){
			if (direction == 1 || direction === '1'){
				time++;
				if (time>24)time = 1;
			}else{
				time --;
				if (time<1)time = 24;
			}
		}

		this.getDay = function () {

				return day;
		};

		this.getMonth = function () {
				return monthString;
		};

		this.getMonthInt = function(){
			return month+1;
		}

		this.getYear = function () {
				return year;
		};

		this.getTime = function(){
			return time;
		}
		
		this.setDate = function(tempyear, tempmonth, tempday, temphour){
			dateObject = new Date(tempyear, tempmonth, tempday, temphour, 0, 0, 0);
			//Get date in numbers
			month = dateObject.getMonth();
			day = dateObject.getDate();
			year = dateObject.getFullYear();
			time = dateObject.getHours();
			currentYear = dateObject.getFullYear();
			//Get date in string format
			monthString = monthsArray[dateObject.getMonth()];
		}

		this.getLocation = function(callback){
			navigator.geolocation.getCurrentPosition(callback,
			function(error){
				console.log(error);
			});
		};

		this.searchLocation = function(query,locations){
			this.getLocation(function(pos){
				var lat = pos.coords.latitude;
				var lng = pos.coords.longitude;
				var url = "https://maps.googleapis.com/maps/api/place/textsearch/json?radius=10000&location="+lat+","+lng+"&key=AIzaSyBinl1su9ywT5WVhBNmKugvdQHziIlCDyY&query="+query;
				$http.get(url).success(function(res){
					var loc = res.results;
					for (id in loc){
						locations.push(loc[id]);
					}
				});
			});
		}
		
		this.getSearchLocation = function(lat, lng, location){
				var url = "https://maps.googleapis.com/maps/api/place/textsearch/json?radius=10000&location="+lat+","+lng+"&key=AIzaSyBinl1su9ywT5WVhBNmKugvdQHziIlCDyY&query=none";
				
				$http.get(url).success(function(res){
					var loc = res.results;
					for (id in loc){
						location = loc[id];
					}
					console.log("ksdkjsdnksd" + JSON.stringify(location));
				});
		}

		this.editEvent = function(base_url,data){
			var url = base_url + "event/";
			return null;
			//return $http.post(url,JSON.stringify(data));
		}

}]);
