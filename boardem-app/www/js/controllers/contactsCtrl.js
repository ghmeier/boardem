appCtrl.controller('ContactsCtrl',function($rootScope,$window,$ionicPopup,$http, $scope, $state,UserService){

	$scope.getUsers = function(){
		$scope.users = UserService.getUserContacts($rootScope.SERVER_LOCATION,$rootScope.user_id);
	}

	$scope.toProfile = function(profile_id){
		window.location.href= "#/app/profile/"+profile_id;
	}

	$scope.removeFriend = function(friend_id){
		UserService.removeFriend($rootScope.SERVER_LOCATION,$rootScope.user_id,friend_id).success(function(res){
			$scope.getUsers();
		});
	}

});