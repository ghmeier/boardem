appCtrl.controller('BrowseCtrl',function($rootScope,$window,$ionicPopup,$http, $scope, $state,UtilService,UserService){
	$scope.getUsers = function(){
		$scope.users = UserService.getUserDetail($rootScope.SERVER_LOCATION,$rootScope.user_id);
	}

	$scope.toProfile = function(profile_id){
		window.location.href= "#/app/profile/"+profile_id;
	}

	$scope.addFriend = function(friend_id){
		UserService.addFriend($rootScope.SERVER_LOCATION,$rootScope.user_id,friend_id).success(function(res){
			UtilService.checkBadges(res);
			$scope.getUsers();
		});
	}

	$scope.removeFriend = function(friend_id){
		UserService.removeFriend($rootScope.SERVER_LOCATION,$rootScope.user_id,friend_id).success(function(res){
			$scope.getUsers();
		});
	}

	$scope.getUsers();
});