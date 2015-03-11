appCtrl.controller('GameCtrl',function($rootScope, $scope,$window, $state, $stateParams, $ionicPlatform, UtilService, GameService) {	
	$scope.games = {};
	$scope.getGame = function(){
		$scope.game = GameService.getSingleGame($rootScope.SERVER_LOCATION,$stateParams.gameId);
	}

	$scope.getGame();
});