appCtrl.controller('GameCtrl',function($rootScope, $scope,$window, $state, $stateParams, $ionicPlatform, UtilService, UserService,GameService,ExperienceService) {
	$scope.game = "";

	$scope.getGame = function(){
		GameService.getSingleGame($rootScope.SERVER_LOCATION,$stateParams.gameId).success(function(res){
			$scope.game = res.extra;
            var temp = [$scope.game];
            var games = [];
            GameService.getGameDetails(temp,games,$rootScope.shelfGames);

		});
	}

    $scope.add = function(game){
        GameService.addToShelf($rootScope.SERVER_LOCATION,$rootScope.user_id,game.name).success(function(res){
            if (res.code == 0 || res.code === "0"){
                game.shelved = true;
                $rootScope.shelfGames = [];
                UtilService.checkBadges(res);
                UserService.getShelf($rootScope.SERVER_LOCATION,$rootScope.user_id,$rootScope.shelfGames);
                var xp = 100;
                ExperienceService.addToUserXP($rootScope.SERVER_LOCATION, $rootScope.user_id, xp);
            }
        });
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

	$scope.getGame();
});