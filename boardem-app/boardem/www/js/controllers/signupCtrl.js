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
			console.log($scope.data.username);
			facebookLogin();
			$scope.toEvents();
		}
	}
});