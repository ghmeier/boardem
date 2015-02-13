var appCtrl =angular.module('starter.controllers', ['ionic'])

.controller('MenuCtrl', function($rootScope, $scope, $ionicHistory) {
	$ionicHistory.clearHistory();
	 $scope.changeDate = function(direction) {
			$rootScope.changeDate(direction);
  };

});

function facebookLogin(username, callback){
	var ref = new Firebase("https://boardem.firebaseio.com");
	ref.authWithOAuthPopup("facebook", function(error,authData){
		callback(error, authData,username);
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
