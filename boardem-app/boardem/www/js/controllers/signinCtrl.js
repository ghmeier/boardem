appCtrl.controller("signinCtrl",function($window,$rootScope,$ionicPopup, $scope,$state,$http){

	$scope.toEvents = function(){
		$state.transitionTo("app.events");
	}

	$scope.toFacebook = function(){
		var response = facebookLogin("",$scope.fbLoginCall);
		
	}

	$scope.fbLoginCall = function(error,authData,username){
		var id = authData.facebook.id;
		var url = $rootScope.SERVER_LOCATION + "signin?facebookId="+id;
		$scope.idLogin(id,$scope.toEvents);
	}

	$scope.idLogin = function(id,callback){
	    $http.get($rootScope.SERVER_LOCATION + "signin?facebookId="+id).
	    success(function(data, status, headers, config) {
	        if (data.code === 0){
	        	console.log(id);
	          $window.localStorage.setItem('id', id);
	          callback();
	      }else {
	        $ionicPopup.alert({
	          title:"Login Error",
	          template:data.message,
	        });
	      }
	    }).
	    error(function(data, status, headers, config) {
	      $ionicPopup.alert({
	            title: "Login Error",
	            template: "Unable to Communicate with server."
	          });
	    });
	};

});