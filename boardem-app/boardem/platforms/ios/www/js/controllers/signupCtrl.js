appCtrl.controller("signupCtrl",function($window,$scope,$rootScope,$http,$state,$ionicPopup,$firebaseAuth){
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
			var response = $scope.facebookLogin($scope.data.username,$scope.fbCallback);
		}
	}

	$scope.fbCallback = function(authData,username){
			var url = $rootScope.SERVER_LOCATION + "signup";
			var id = authData.facebook.id;

			$http.post(url,{username:username,facebook_id:authData.facebook.id,display_name:authData.facebook.displayName,picture_url:authData.facebook.cachedUserProfile.picture.data.url}).
			success(function(data, status, headers, config) {
			  	// this callback will be called asynchronously
			  	// when the response is available
			  	if (data.code === 0){
			  		$window.localStorage.setItem('id', id);
			  		$scope.toEvents();
				}else {
					$ionicPopup.alert({
		          		title: "Login Error",
		          		template: data.message,
		        	});					
				}
			}).
			error(function(data, status, headers, config) {
				$ionicPopup.alert({
	          		title: "Login Error",
	          		template: "Unable to Communicate with server."
	        	});
			});
	}

	$scope.facebookLogin = function(username, callback){
		var ref = new Firebase("https://boardem.firebaseio.com");
		var authRef = $firebaseAuth(ref);

		authRef.$authWithOAuthPopup("facebook").then(function(authData){
			callback(authData,username);
		}).catch(function(error){
			$ionicPopup.alert({
          		title: "Login Error",
          		template: error
        	});
	  	});
	}
});