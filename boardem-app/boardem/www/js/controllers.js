var appCtrl =angular.module('starter.controllers', ['ionic'])

.controller('MenuCtrl', function($scope, $ionicHistory) {
	$ionicHistory.clearHistory();
	console.log("ere");
});

function facebookLogin(){
	var ref = new Firebase("https://boardem.firebaseio.com");
	ref.authWithOAuthPopup("facebook", function(error, authData) {
	 	if (error) {
			$ionicPopup.alert({
          		title: "Login Error",
          		template: error
        	});
		} else {
	    	console.log("Authenticated successfully with payload:", JSON.stringify(authData.facebook));
		}
	});
}
