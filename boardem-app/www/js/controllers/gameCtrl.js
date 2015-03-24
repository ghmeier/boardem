appCtrl.controller('GameCtrl',function($rootScope, $scope,$window, $state, $stateParams, $ionicPlatform, UtilService, GameService) {	
	$scope.game = "";
	$scope.getGame = function(){
		GameService.getSingleGame($rootScope.SERVER_LOCATION,$stateParams.gameId).success(function(res){
			$scope.game = res.extra;
			$scope.game.image = $scope.game.image.substr(2);
			console.log($scope.game.image);
			//console.log($scope.game.image);
		});
	}

	$scope.getGame();
});