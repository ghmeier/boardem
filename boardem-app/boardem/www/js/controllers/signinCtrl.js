appCtrl.controller("signinCtrl",function($rootScope, $scope,$state,$http){

	$scope.toEvents = function(){
		$state.transitionTo("app.events");
	}

	$scope.toFacebook = function(){
		var response = facebookLogin("",$scope.fbLoginCall);
		
	}

	$scope.fbLoginCall = function(error,authData){
		$http.get($rootScope.SERVER_LOCATION + "signin?facebookId="+authData.facebook.id).
		success(function(data, status, headers, config) {
		  // this callback will be called asynchronously
		  // when the response is available
		  console.log(data,status,headers,config);
		  $scope.toEvents();
		}).
		error(function(data, status, headers, config) {
		  // called asynchronously if an error occurs
			$ionicPopup.alert({
	        	title: "Login Error",
	        	template: "Unable to Communicate with server."
	        });
		});
	}

});