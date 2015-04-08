appCtrl.controller('GamesCtrl',function($rootScope, $scope,$window, $state, $ionicPlatform, UtilService, UserService,GameService, ExperienceService) {
	$scope.games = [];
	$scope.fullView = true;
	$scope.page = 0;

	$scope.initShelf = function(){
		if ($rootScope.shelfGames.length == 0){
			UserService.getShelf($rootScope.SERVER_LOCATION,$rootScope.user_id,$rootScope.shelfGames).then(function(){
				$scope.page = 0;
				$scope.games = [];
				$scope.getGames();
			});
		}else{
			$scope.getGames();
		}
	}

	$scope.getGames = function(){
		GameService.getAllGames($rootScope.SERVER_LOCATION,$scope.games,$scope.page,$rootScope.shelfGames);
		$scope.page++;
		$scope.$broadcast('scroll.infiniteScrollComplete');
	}

	$scope.add = function(game){
		GameService.addToShelf($rootScope.SERVER_LOCATION,$rootScope.user_id,game.name).success(function(res){
			if (res.code == 0 || res.code === "0"){
				game.shelved = true;
				$rootScope.shelfGames = [];
				UtilService.checkBadges(res);
				UserService.getShelf($rootScope.SERVER_LOCATION,$rootScope.user_id,$rootScope.shelfGames);
				var xp = Math.round(Math.random() * (150 - 50) + 50);
				ExperienceService.addToUserXP($rootScope.SERVER_LOCATION, $rootScope.user_id, xp);
				$rootScope.xp_info = [];
				ExperienceService.updateUserXPInfo($rootScope.SERVER_LOCATION, $rootScope.user_id, $rootScope.xp_info);
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

	$scope.toGame = function(game_name){
		$window.location.href = "#/app/game/"+game_name;
	}

	$scope.switchView = function(val) {
			if(val == 1 || val == "1") {
				$scope.fullView = true;
			} else {
				$scope.fullView = false;
			}
		}
});