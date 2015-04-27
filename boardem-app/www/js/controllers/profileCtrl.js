appCtrl.controller('profileCtrl',function($window,$rootScope,$http, $scope, $state,$stateParams,UtilService, UserService, ExperienceService){
	$scope.user={};
	$scope.invite = $stateParams.profileId != $rootScope.user_id;
	$scope.userBadges = [];
	$scope.userShelf = [];
	//$scope.initProfile();

	UserService.getUser($rootScope.SERVER_LOCATION,$stateParams.profileId).
	    success(function(data) {
  			UserService.getBadges($rootScope.SERVER_LOCATION,$stateParams.profileId,$scope.userBadges);
  			UserService.getShelf($rootScope.SERVER_LOCATION,$stateParams.profileId,$scope.userShelf);
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

	$scope.toGame = function(game_name){
		$window.location.href = "#/app/game/"+game_name;
	}

});