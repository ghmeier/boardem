appCtrl.controller('createEventCtrl', function($rootScope, $scope,$window,$ionicPopup, $ionicModal, $state, UtilService, CreateEventService) {
  $scope.data = {games : '',query:''};
	$scope.eventDay = CreateEventService.getDay();
	$scope.eventMonth = CreateEventService.getMonth();
	$scope.eventYear = CreateEventService.getYear();
	$scope.eventTime = CreateEventService.getTime();
	$scope.eventLocation = {};
	$scope.locations = [];


	$ionicModal.fromTemplateUrl('../templates/location.html', {
	    scope: $scope,
	    animation: 'slide-in-up'
	  }).then(function(modal) {
	    $scope.modal = modal;
	  });

	  $ionicModal.fromTemplateUrl('../templates/games-modal.html', {
	    scope: $scope,
	    animation: 'slide-in-up'
	  }).then(function(modal) {
	    $scope.gModal = modal;
	  });

	$scope.changeEventDay = function(direction) {
			CreateEventService.changeEventDay(direction);
			$scope.eventDay = CreateEventService.getDay();
	}

	$scope.changeEventMonth = function(direction) {
			CreateEventService.changeEventMonth(direction);
			$scope.eventMonth = CreateEventService.getMonth();
	}

	$scope.changeEventYear = function(direction) {
			CreateEventService.changeEventYear(direction);
			$scope.eventYear = CreateEventService.getYear();
	}

	$scope.changeEventTime = function(direction){
			CreateEventService.changeEventTime(direction);
			$scope.eventTime = CreateEventService.getTime();
	}

	$scope.createEvent = function() {
			CreateEventService.getLocation(function(pos){
				var loc = {};
				var day = CreateEventService.getDay();
				var month = CreateEventService.getMonthInt();
				var year = CreateEventService.getYear();
				var time = CreateEventService.getTime();

				if (!$scope.eventLocation.geometry){
					UtilService.popup("No Location","Please select a location to host your event.");
					return;
				}

				loc.lat = $scope.eventLocation.geometry.location.lat;
				loc.lng = $scope.eventLocation.geometry.location.lng;
				loc.owner = $rootScope.user_id;
				loc.games = $scope.data.games.split(',');
				loc.name = $scope.data.name;
				loc.date = year+"-"+month+"-"+day+" "+time+":00:00";

				CreateEventService.createEvent($rootScope.SERVER_LOCATION,loc).success(function(response){
			      	UtilService.popup("Success","Added Event: "+response.extra);
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

	$scope.selectLocation = function(location){
		$scope.eventLocation = location;
	}

	$scope.locationSearch = function(){
		$scope.locations = [];
		CreateEventService.searchLocation($scope.data.query,$scope.locations);
	}

	$scope.gamesModal = function(){
		$scope.gModal.show();
	}
});

appCtrl.service('CreateEventService', ['$http', function ($http) {

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
					console.log(locations);
				});
			});
		}

		this.createEvent = function(base_url,data){
			var url = base_url + "event/";

			return $http.post(url,JSON.stringify(data));
		}

}]);
