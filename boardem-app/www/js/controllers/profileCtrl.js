appCtrl.controller('profileCtrl',function($rootScope,$http, $scope, $state,$stateParams,UtilService, UserService, ExperienceService){
	$scope.user={};
	$scope.invite = $stateParams.profileId != $rootScope.user_id;
	//$scope.initProfile();
	
	UserService.getUser($rootScope.SERVER_LOCATION,$stateParams.profileId).
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
			
	$scope.initProfile = function(){
		$rootScope.xp_info = [];
		ExperienceService.updateUserXPInfo($rootScope.SERVER_LOCATION, $rootScope.user_id, $rootScope.xp_info);
	}

});