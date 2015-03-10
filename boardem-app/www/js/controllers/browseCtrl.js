appCtrl.controller('BrowseCtrl',function($rootScope,$window,$ionicPopup,$http, $scope, $state,UserService){
	$scope.getUsers = function(){
		$scope.users = UserService.getUserDetail($rootScope.SERVER_LOCATION,$rootScope.user_id);
	}

	$scope.toProfile = function(profile_id){
		window.location.href= "#/app/profile/"+profile_id;
	}

	$scope.getUsers();
});