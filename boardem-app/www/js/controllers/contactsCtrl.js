appCtrl.controller('ContactsCtrl',function($rootScope,$window,$ionicPopup,$http, $scope, $state,UserService,FacebookService){
	$scope.fbFriends = [];

	$scope.initUsers = function(){
		FacebookService.getFriends($rootScope.user_id,$rootScope.token,$scope.fbFriends).then(function(){
		//if ($scope.fbFriends.length > 0){
			$scope.$broadcast('scroll.infiniteScrollComplete');

		});
		//}
	}

	$scope.invite = function(id){
		console.log(id);
		FB.ui({
		    method: 'apprequests',
		    message: 'Boardem Invitation!',
		    to: id
		  },
		  function(res){
		  	console.log(res);
		  }
		);
	}

	$scope.getUsers = function(){
		$scope.users = UserService.getUserContacts($rootScope.SERVER_LOCATION,$rootScope.user_id);
		//$scope.initUsers();
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