appCtrl.controller('profileCtrl',function($rootScope,$window,$ionicPopup,$http, $scope, $state,UserService){
	$scope.user={};
	$scope.url = $rootScope.SERVER_LOCATION + "users/"+$window.localStorage['id'];
	UserService.getUser($scope.url).
	    success(function(data) {
	        if (data.code === 0){
	        	console.log(data.extra);
	        	$scope.user = data.extra;
	      }else {
	        $ionicPopup.alert({
	          title:"Login Error",
	          template:data.message,
	        });
	      }
	    }).
	    error(function(data) {
	      $ionicPopup.alert({
	            title: "Login Error",
	            template: "Unable to Communicate with server."
	          });
	    });

});

appCtrl.service("UserService",['$http',function($http){


	this.getUser = function(url){
		return $http.get(url);
	}
}]);