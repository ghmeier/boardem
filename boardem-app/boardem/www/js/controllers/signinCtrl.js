appCtrl.controller("signinCtrl",function($scope,$state){
	
	$scope.toEvents = function(){
		$state.transitionTo("app.events");
	}

	$scope.toFacebook = function(){
		facebookLogin();
		$scope.toEvents();
	}
});