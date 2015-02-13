appCtrl.controller("signupCtrl",function($scope,$state,$ionicPopup){
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
			facebookLogin($scope.data.username,$scope.fbCallback);
			$scope.toEvents();
		}
	}

	$scope.fbCallback = function(error,authData,username){
	 	if (error) {
			$ionicPopup.alert({
          		title: "Login Error",
          		template: error
        	});
		} else {
	    	return (authData.facebook);
		}
	}
});