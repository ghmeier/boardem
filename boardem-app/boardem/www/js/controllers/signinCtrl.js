appCtrl.controller("signinCtrl",function($rootScope, $scope,$state,$http){

	$scope.toEvents = function(){
		$state.transitionTo("app.events");
	}

	$scope.toFacebook = function(){
		$http.get($rootScope.SERVER_LOCATION + "signin").
		success(function(data, status, headers, config) {
		  // this callback will be called asynchronously
		  // when the response is available
		  console.log(data,status,headers,config);
		}).
		error(function(data, status, headers, config) {
		  // called asynchronously if an error occurs
		  // or server returns response with an error status.
		  console.log("wow");
		});
		facebookLogin();
		$scope.toEvents();
	}
});