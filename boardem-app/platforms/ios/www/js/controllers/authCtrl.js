appCtrl.controller('AuthCtrl',function($rootScope,$window,$ionicPopup,$http, $scope, $state){

	$scope.idLogin = function(id,callback){
	    $http.get($rootScope.SERVER_LOCATION + "signin?facebookId="+id).
	    success(function(data, status, headers, config) {
	        if (data.code === 0){
	          $window.localStorage.setItem('id', id);
	          callback();
	      }else {
	      	$state.transitionTo("login.signup");
	      }
	    }).
	    error(function(data, status, headers, config) {
	      $state.transitionTo("login.signup");
	    });
	};

	if (window.localStorage['id'] ===null ||window.localStorage['id'] ===undefined || window.localStorage['id'] === ""){
		$state.transitionTo("login.signup");
	}else {
		var id = $window.localStorage['id'];
		console.log(id);
		var url = $rootScope.SERVER_LOCATION + "signin?facebookId="+id;
		$scope.idLogin(id,function(){$state.transitionTo('app.events');});
	}

});