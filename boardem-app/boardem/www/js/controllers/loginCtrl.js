appCtrl.controller('loginCtrl',function($scope,$state){
	$scope.toSignin = function(){
		$state.transitionTo("login.signin");
	}

	$scope.toSignup = function(){
		$state.transitionTo("login.signup");
	}
});