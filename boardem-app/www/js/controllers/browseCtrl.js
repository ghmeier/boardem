appCtrl.controller('BrowseCtrl',function($rootScope,$window,$ionicPopup,$http, $scope, $state,UserService){

	$scope.getUsers = function(){
		service = new UserService();
		$scope.users = service.getUsers($rootScope.SERVER_LOCATION);
	}

	$scope.getUsers();
});