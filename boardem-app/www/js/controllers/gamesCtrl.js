appCtrl.controller('GamesCtrl',function($rootScope, $scope,$window, $state, $ionicPlatform, UtilService, GameService) {	
	$scope.games = [];
	$scope.getGames = function(){
		$scope.games = GameService.getAllGames($rootScope.SERVER_LOCATION);
	}

	$scope.toGame = function(id){
		$window.location.href = "#/app/game/"+id;
	}
	$scope.getGames();
});