appCtrl.controller("signinCtrl",function($scope,$state){

	$scope.toEvents = function(){
		$state.transitionTo("app.events");
	}

	$scope.toFacebook = function(){
		$http.get(SERVER_LOCATION + "signin");
		facebookLogin();
		$scope.toEvents();
	}
});