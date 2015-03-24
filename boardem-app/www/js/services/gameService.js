/*-----------------------------------------------------
								GAME SERVICE
-----------------------------------------------------*/
appCtrl.service("GameService",['$http',function($http){

	var endpoint = "games/";

	this.getGames = function(base_url, page_num){

		return $http.get(base_url+"games?page_number="+page_num);
	}

	this.getSingleGame = function(base_url,name){
			return $http.get(base_url+"games/"+name);;
	}

	this.getAllGames = function(base_url,games,page,shelf){
		this.getGames(base_url, page).success(function(res){
			var gameRaw = res.extra;

			for (id in gameRaw){
				gameRaw[id].image = (gameRaw[id].image).substr(2);
				for (i in shelf){
					console.log(shelf[i].name,gameRaw[id].name);
					if (shelf[i].name === gameRaw[id].name){
						gameRaw[id].shelved = true;
						console.log("hmm");
						break;
					}
				}
				games.push(gameRaw[id]);
			}
		});
	}

	this.addToShelf = function(base_url,user_id,game_id){
		return $http.post(base_url+"users/"+user_id+"/shelf?sid="+game_id);
	}
}]);