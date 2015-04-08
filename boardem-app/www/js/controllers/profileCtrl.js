appCtrl.controller('profileCtrl',function($rootScope,$http, $scope, $state,$stateParams,UtilService, UserService, ExperienceService){
	$scope.user={};
	$scope.invite = $stateParams.profileId != $rootScope.user_id;
	$scope.userBadges = [];
	//$scope.initProfile();

	UserService.getUser($rootScope.SERVER_LOCATION,$stateParams.profileId).
	    success(function(data) {
  			UserService.getBadges($rootScope.SERVER_LOCATION,$stateParams.profileId,$scope.userBadges);

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
		$scope.xp_info = [];
		ExperienceService.updateUserXPInfo($rootScope.SERVER_LOCATION, $stateParams.profileId, $scope.xp_info);
	}

});