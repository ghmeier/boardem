appCtrl.controller("signupCtrl",function($window,$scope,$rootScope,$http,$state,$firebaseAuth,UtilService){
	$scope.data = {username : ''};

	$scope.toEvents = function(){
		$state.transitionTo("app.events");
	}

	$scope.toFacebook = function(){
		if ($scope.data.username===""){
			UtilService.popup("Login Error","We'll need a username.");
		}else{
			var response = $scope.facebookLogin($scope.data.username,$scope.fbCallback);
		}
	}

	$scope.fbCallback = function(authData,username){
			var url = $rootScope.SERVER_LOCATION + "signup";
			var id = authData.facebook.id;
			$rootScope.token = authData.facebook.accessToken;

			$http.post(url,{username:username,facebook_id:authData.facebook.id,display_name:authData.facebook.displayName,picture_url:authData.facebook.cachedUserProfile.picture.data.url}).
			success(function(data, status, headers, config) {
			  	if (data.code === 0){
			  		$window.localStorage.setItem('id', id);
			  		$scope.toEvents();
				}else {
					UtilService.popup("Login Error",data.message);
				}
			}).
			error(function(data, status, headers, config) {
				UtilService.popup("Login Error","Unable to Communicate with server.");
			});
	}

	$scope.facebookLogin = function(username, callback){
		var ref = new Firebase("https://boardem.firebaseio.com");
		var authRef = $firebaseAuth(ref);

		authRef.$authWithOAuthPopup("facebook").then(function(authData){
			callback(authData,username);
		}).catch(function(error){
			UtilService.popup("Login Error",error);
	  	});
	}
});