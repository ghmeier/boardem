appCtrl.controller('profileCtrl',function($rootScope,$window,$ionicPopup,$http, $scope, $state){
	$scope.username = "";
	$scope.display_name = "";
	$scope.picture_url = "";
	$http.get($rootScope.SERVER_LOCATION + "users/"+$window.localStorage['id']).
	    success(function(data, status, headers, config) {
	        if (data.code === 0){
	        	console.log(data.extra);
	        	$scope.username = data.extra.username;
	        	$scope.display_name = data.extra.display_name;
	        	$scope.picture_url = data.extra.picture_url;
	      }else {
	        $ionicPopup.alert({
	          title:"Login Error",
	          template:data.message,
	        });
	      }
	    }).
	    error(function(data, status, headers, config) {
	      $ionicPopup.alert({
	            title: "Login Error",
	            template: "Unable to Communicate with server."
	          });
	    });

});