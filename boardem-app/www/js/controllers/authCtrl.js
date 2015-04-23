appCtrl.controller('AuthCtrl',function($rootScope,$window,$ionicPopup,$http, $scope, $state,$firebaseAuth,UtilService){

	$scope.idLogin = function(id,callback){
		UtilService.showLoad();
	    $http.get("http://proj-309-16.cs.iastate.edu:8080/" + "signin?facebookId="+id).
	    success(function(data, status, headers, config) {
	    	UtilService.hideLoad();
	      if (data.code === 0){
	         $window.localStorage.setItem('id', id);
	         var response = $scope.facebookLogin("",$scope.fbLoginCall,callback);

	      }else {
	      	$state.transitionTo("login.signup");
	      }
	    }).
	    error(function(data, status, headers, config) {
	    	UtilService.hideLoad();
	      $state.transitionTo("login.signup");
	    });
	};

	$scope.facebookLogin = function(username, callback,cb){
		var ref = new Firebase("https://boardem.firebaseio.com");
		var authRef = $firebaseAuth(ref);

		authRef.$authWithOAuthPopup("facebook",{
		  remember: "sessionOnly",
		  scope: "public_profile,user_friends"
		}).then(function(authData){
			callback(authData,username,cb);
		}).catch(function(error){
			UtilService.popup("Login Error",error);
	  	});
	}

	$scope.fbLoginCall = function(authData,username,callback){
		var id = authData.facebook.id;
		$rootScope.token = authData.facebook.accessToken;
		callback();
	}

	if (window.localStorage['id'] ===null ||window.localStorage['id'] ===undefined || window.localStorage['id'] === ""){
		$state.transitionTo("login.signup");
	}else {
		var id = $window.localStorage['id'];
		$scope.idLogin(id,function(){$state.transitionTo('app.events');});
	}

});