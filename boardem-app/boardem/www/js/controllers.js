var appCtrl =angular.module('starter.controllers', ['ionic'])

.controller('MenuCtrl', function($rootScope, $scope, $ionicHistory) {
	$ionicHistory.clearHistory();
	 $scope.changeDate = function(direction) {
			$rootScope.changeDate(direction);
  };

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

function safeApply($scope, $root, fn) {
  var phase = $root.$$phase;
  if (phase == '$apply' || phase == '$digest') {
    if (fn && (typeof(fn) === 'function')) {
      fn();
    }
  } else {
    $scope.$apply(fn);
  }
};
