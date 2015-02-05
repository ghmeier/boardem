appCtrl.controller("signupCtrl",function($scope,$state){

	$scope.toEvents = function(){
		$state.transitionTo("app.events");
	}	
});