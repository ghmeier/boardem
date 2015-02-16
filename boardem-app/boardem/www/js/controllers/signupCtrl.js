appCtrl.controller("signupCtrl",function($scope,$rootScope,$http,$state,$ionicPopup){
	$scope.data = {username : ''};

	$scope.toEvents = function(){
		$state.transitionTo("app.events");
	}	

	$scope.toFacebook = function(){
		if ($scope.data.username===""){
			$ionicPopup.alert({
          		title: "Login Error",
          		template: "We'll need a username."
        	});
		}else{
			var response = facebookLogin($scope.data.username,$scope.fbCallback);
		}
	}

	$scope.fbCallback = function(error,authData,username){
	 	if (error) {
			$ionicPopup.alert({
          		title: "Login Error",
          		template: error
        	});
		} else {
			console.log(authData);
			var url = $rootScope.SERVER_LOCATION + "signup";
		
			$http.post(url,{username:username,facebookId:authData.facebook.id,displayName:authData.facebook.displayName,pictureUrl:authData.facebook.cachedUserProfile.picture.url}).
			success(function(data, status, headers, config) {
			  // this callback will be called asynchronously
			  // when the response is available
			  console.log(data,status,headers,config);
			  $scope.toEvents();
			}).
			error(function(data, status, headers, config) {
				$ionicPopup.alert({
	          		title: "Login Error",
	          		template: "Unable to Communicate with server."
	        	});
			});
		}
	}
});