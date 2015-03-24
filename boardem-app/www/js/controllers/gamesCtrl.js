appCtrl.controller('GamesCtrl',function($rootScope, $scope,$window, $state, $ionicPlatform, UtilService, UserService,GameService) {
	$scope.games = [];
	$scope.fullView = true;
	$scope.page = 0;
	$scope.shelf = [];

	$scope.initShelf = function(){
		if ($scope.shelf.length == 0){
			UserService.getShelf($rootScope.SERVER_LOCATION,$rootScope.user_id,$scope.shelf).then(function(){
				$scope.page = 0;
				$scope.games = [];
			});
		}
	}

	$scope.getGames = function(){
		GameService.getAllGames($rootScope.SERVER_LOCATION,$scope.games,$scope.page,$scope.shelf);
		$scope.page++;
		$scope.$broadcast('scroll.infiniteScrollComplete');
	}

	$scope.add = function(game){
		GameService.addToShelf($rootScope.SERVER_LOCATION,$rootScope.user_id,game.name).success(function(res){
			if (res.code == 0 || res.code === "0"){
				game.shelved = true;
			}
		});
	}

	$scope.remove = function(game){
		GameService.removeFromShelf($rootScope.SERVER_LOCATION,$rootScope.user_id,game.name).success(function(res){
			if (res.code == 0 || res.code === "0"){
				game.shelved = false;
			}
		})
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