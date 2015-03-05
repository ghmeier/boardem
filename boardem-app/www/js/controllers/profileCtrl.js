appCtrl.controller('profileCtrl',function($rootScope,$http, $scope, $state,UtilService, UserService){
	$scope.user={};

	UserService.getUser($rootScope.SERVER_LOCATION,$rootScope.user_id).
	    success(function(data) {
	        if (data.code === 0){
	        	$scope.user = data.extra;
	      }else {
	      	UtilService.popup("Login Error",data.message);
	      }
	    }).
	    error(function(data) {
	    	UtilService.popup("Login Error","Unable to Communicate with server.");
	    });

});