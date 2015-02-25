var appCtrl =angular.module('starter.controllers', ['ionic','firebase'])

.controller('MenuCtrl', function($rootScope, $scope, $ionicHistory, $window, SearchCriteria, RestService) {

	$scope.day = SearchCriteria.getDay();
	$scope.month = SearchCriteria.getMonth();
	$scope.year = SearchCriteria.getYear();
	$scope.locationSearch = SearchCriteria.getLocationSearch();
	
	$ionicHistory.clearHistory();

	//Actions
	$scope.changeDate = function(direction){
			SearchCriteria.changeDateSearch(direction);
			$scope.day = SearchCriteria.getDay();
			$scope.month = SearchCriteria.getMonth();
			$scope.year = SearchCriteria.getYear();
			//$rootScope.events = RestService.getEvents(($scope.month + " " + $scope.day + " " + $scope.year));
	}
	
	$scope.changeLocationSearch = function(direction){
			SearchCriteria.changeDistance(direction);
			$scope.locationSearch = SearchCriteria.getLocationSearch();
	}

});

appCtrl.service('SearchCriteria', function () {
		
		var dateObject = new Date();
		var daysArray = ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'];
		var monthsArray = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
		//Get date in numbers
		var month = dateObject.getMonth();
		var day = dateObject.getDate();
		var year = dateObject.getFullYear();
		//Get date in string format
		var monthString = monthsArray[dateObject.getMonth()];
		var dayString = daysArray[dateObject.getDay()];
	
		var locationSearch = 5;
		var gameType = "all";
		
		/*Date based search features*/
		this.changeDateSearch = function (direction) {
					
				var newDateObject = new Date(year, month, day);
				if(direction == 1 || direction == "1") {
					newDateObject.setDate(newDateObject.getDate()+1);
				} else {
					newDateObject.setDate(newDateObject.getDate()-1);
				}
			
				//Get date in numbers
				month = newDateObject.getMonth();
				day = newDateObject.getDate();
				year = newDateObject.getFullYear();
				//Get date in string format
				monthString = monthsArray[newDateObject.getMonth()];
				dayString = daysArray[newDateObject.getDay()];
		};
		
		this.getSearchDate = function () {
					
				var datestring = day + " " + monthString + " " + year;
				return datestring;
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
		
		/*Location based search features*/
		this.changeDistance = function(direction){
				if(direction == 1 || direction == "1") {
					if(locationSearch<10)locationSearch++;
					else if(locationSearch<30)locationSearch = locationSearch+5;
					else if(locationSearch < 50) locationSearch = locationSearch+10;
				}else{
					if(locationSearch>30)locationSearch = locationSearch-10;
					else if(locationSearch>10)locationSearch = locationSearch-5;
					else if(locationSearch > 1) locationSearch--;
				}
		}
		
		this.getLocationSearch = function () {
				
				return locationSearch;
		};
		
		
});

function safeApply($scope, $root, fn) {
  var phase = $root.$$phase;
  if (phase == '$apply' || phase == '$digest') {
    if (fn && (typeof(fn) === 'function')) {
      fn();
    }
  } else {
    $scope.$apply(fn);
  }
};
