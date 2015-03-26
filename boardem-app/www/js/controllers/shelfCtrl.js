appCtrl.controller('shelfCtrl',function($rootScope, $scope,$window, $state, $ionicPlatform, UserService, UtilService, GameService) {
	$scope.fullView = true;

	$scope.getShelf = function(){
		$rootScope.shelfGames = [];
		UserService.getShelf($rootScope.SERVER_LOCATION,$rootScope.user_id,$rootScope.shelfGames);
	}

	$scope.toGame = function(game_name){
		$window.location.href = "#/app/game/"+game_name;
	}

	$scope.remove = function(game){
		GameService.removeFromShelf($rootScope.SERVER_LOCATION,$rootScope.user_id,game.name).success(function(res){
			if (res.code == 0 || res.code === "0"){
				game.shelved = false;
				$rootScope.shelfGames = [];
				UserService.getShelf($rootScope.SERVER_LOCATION,$rootScope.user_id,$rootScope.shelfGames);
			}
		});
	}

	$scope.switchView = function(val) {
			if(val == 1 || val == "1") {
				$scope.fullView = true;
			} else {
				$scope.fullView = false;
			}
	}
});