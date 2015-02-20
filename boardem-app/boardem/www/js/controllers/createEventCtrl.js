appCtrl.controller('createEventCtrl', function($rootScope, $scope, CreateEventService) {
  $scope.data = {games : ''};
	$scope.eventDay = CreateEventService.getDay();
	$scope.eventMonth = CreateEventService.getMonth();
	$scope.eventYear = CreateEventService.getYear();
	
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
	
	$scope.createEvent = function() {
			//Get Longitude and Latitude
			//Send request to server to create this event
	}
});

appCtrl.service('CreateEventService', function () {
		
		var dateObject = new Date();
		var daysArray = ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'];
		var monthsArray = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
		//Get date in numbers
		var month = dateObject.getMonth();
		var day = dateObject.getDate();
		var year = dateObject.getFullYear();
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
		
		this.getDay = function () {
					
				return day;
		};
		
		this.getMonth = function () {
				return monthString;
		};
		
		this.getYear = function () {
				return year;
		};
		
});
