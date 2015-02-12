appCtrl.controller('loginCtrl',function($rootScope, $scope,$state){
	$scope.toSignin = function(){
		$state.transitionTo("login.signin");
	}

	$scope.toSignup = function(){
		$state.transitionTo("login.signup");
	}
});