var appCtrl =angular.module('starter.controllers', ['ionic','firebase'])

.controller('MenuCtrl', function($rootScope, $scope, $ionicHistory,SearchCriteria) {
	
	var dateObject = new Date();
	$scope.daysArray = ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'];
	$scope.monthsArray = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
	//Get date in numbers
  $scope.month = dateObject.getMonth();
	$scope.day = dateObject.getDate();
	$scope.year = dateObject.getFullYear();
	//Get date in string format
	$scope.monthString = $rootScope.monthsArray[dateObject.getMonth()];
	$scope.dayString = $rootScope.daysArray[dateObject.getDay()];
	
	$scope.locationSearch = "1";
	$scope.gameType = "all";

	$ionicHistory.clearHistory();
	 $scope.changeDate = function(direction) {
			$rootScope.changeDate(direction);
  };
	
	//Actions
	$scope.changeDate = function(direction){
			SearchCriteria.changeDateSearch(direction);
	}

});

.service('SearchCriteria', ['$http', function ($scope, $http) {
	
		this.changeDateSearch = function (direction) {
					
				var dateObject = new Date($scope.year, $scope.month, $scope.day);
				if(direction == 1 || direction == "1") {
					dateObject.setDate(dateObject.getDate()+1);
				} else {
					dateObject.setDate(dateObject.getDate()-1);
				}
			
				//Get date in numbers
				$scope.month = dateObject.getMonth();
				$scope.day = dateObject.getDate();
				$scope.year = dateObject.getFullYear();
				//Get date in string format
				$scope.monthString = $scope.monthsArray[dateObject.getMonth()];
				$scope.dayString = $scope.daysArray[dateObject.getDay()];
				//It will look like this
				//return $http.get(urlBase);
		};
}]);

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
