appCtrl.controller('profileCtrl',function($rootScope,$window,$ionicPopup,$http, $scope, $state,UserService){
	$scope.user={};

	UserService.getUser($rootScope.SERVER_LOCATION,$window.localStorage['id']).
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

	var endpoint = "users/"
	this.getUser = function(base_url,userid){
		return $http.get(base_url+endpoint+userid);
	}
}]);