appCtrl.controller("signupCtrl",function($rootScope, $scope,$state){

	$scope.toEvents = function(){
		$state.transitionTo("app.events");
	}	

	$scope.toFacebook = function(){
		facebookLogin();
		$scope.toEvents();
	}
});