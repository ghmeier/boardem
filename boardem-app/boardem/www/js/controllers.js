var appCtrl =angular.module('starter.controllers', ['ionic'])

.controller('MenuCtrl', function($rootScope, $scope) {
		
	 $scope.changeDate = function(direction) {
			$rootScope.changeDate(direction);
  };
	 
});

function facebookLogin(){
	var ref = new Firebase("https://boardem.firebaseio.com");
	ref.authWithOAuthPopup("facebook", function(error, authData) {
	 	if (error) {
			console.log("Login Failed!", error);
		} else {
	    	console.log("Authenticated successfully with payload:", JSON.stringify(authData.facebook));
		}
	});
}
