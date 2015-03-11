/*-----------------------------------------------------
								GAME SERVICE
-----------------------------------------------------*/
appCtrl.service("GameService",['$http',function($http){

	var endpoint = "/games/"
	var games = ["Catan"];
	var game = {
	  "artists" : [ "Volkan Baga", "Tanja Donner", "Pete Fenlon", "Jason Hawkins", "Michaela Kienle", "Harald Lieske", "Michael Menzel", "Marion Pott", "Matt Schwabel", "Franz Vohwinkel", "Stephen Graham Walsh" ],
	  "averageRating" : 7.36942,
	  "bggRating" : 7.2379,
	  "description" : "In Catan (formerly The Settlers of Catan), players try to be the dominant force on the island of Catan by building settlements, cities, and roads. On each turn dice are rolled to determine what resources the island produces. Players collect these resources (cards) - wood, grain, brick, sheep, or stone - to build up their civilizations to get to 10 victory points and win the game.&#10;&#10;Setup includes randomly placing large hexagonal tiles (each showing a resource or the desert) in a honeycomb shape and surrounding them with water tiles, some of which contain ports of exchange. Number disks, which will correspond to die rolls (two 6-sided dice are used), are placed on each resource tile. Each player is given two settlements (think, houses) and roads (sticks) which are, in turn, placed on intersections and borders of the resource tiles. Players collect a hand of resource cards based on which hex tiles their last-placed house is adjacent to. A robber pawn is placed on the desert tile.&#10;&#10;A turn consists of possibly playing a development card, rolling the dice, everyone (perhaps) collecting resource cards based on the roll and position of houses (or upgraded cities - think, hotels) unless a 7 is rolled, turning in resource cards (if possible and desired) for improvements, trading cards at a port, and trading resource cards with other players. If a 7 is rolled, the active player moves the robber to a new hex tile and steals resource cards from other players who have built structures adjacent to that tile.&#10;&#10;Points are accumulated by building settlements and cities, having the longest road and the largest army (from some of the development cards), and gathering certain development cards that simply award victory points. When a player has gathered 10 points (some of which may be held in secret), he announces his total and claims the win.&#10;&#10;Catan has won multiple awards and is one of the most popular games in recent history due to its amazing ability to appeal to experienced gamers as well as those new to the hobby.&#10;&#10;Die Siedler von Catan was originally published by Kosmos and has gone through multiple editions. It was licensed by Mayfair and has undergone four editions as The Settlers of Catan. In 2015, it was formally renamed Catan to better represent itself as the core and base game of the Catan series. It has been re-published in two travel editions &ndash; portable edition and compact edition, as a special gallery edition (replaced in 2009 with a family edition), as an anniversary wooden edition, as a deluxe 3D collector's edition, in the basic Simply Catan, as a beginner version, and with an entirely new theme in Japan and Asia as Settlers of Catan: Rockman Edition. Numerous spin-offs and expansions have also been made for the game.&#10;&#10;The Settlers of Catan is the original game in the Catan Series.&#10;&#10;",
	  "designers" : [ "Klaus Teuber" ],
	  "gameId" : 13,
	  "image" : "//cf.geekdo-images.com/images/pic2419375.jpg",
	  "isExpansion" : false,
	  "maxPlayers" : 4,
	  "mechanics" : [ "Dice Rolling", "Hand Management", "Modular Board", "Route/Network Building", "Trading" ],
	  "minPlayers" : 3,
	  "name" : "Catan",
	  "playingTime" : 90,
	  "publishers" : [ "999 Games", "Albi", "Astrel Games", "Brain Games", "Capcom", "Competo / Marektoy", "danspil", "Descartes Editeur", "Devir", "Dexy Co", "Eurogames", "Filosofia Édition", "Galakta", "Giochi Uniti", "GP Games", "Grow Jogos e Brinquedos", "HaKubia", "Hanayama", "Hobby World", "Ideal Board Games", "IntelliGames.BG", "Ísöld ehf.", "Kaissa Chess & Games", "Korea Boardgames co., Ltd.", "KOSMOS", "L&M Games", "Laser plus", "Lautapelit.fi", "Logojogos", "Mayfair Games", "Ninive Games", "Piatnik", "Smart Ltd", "Stupor Mundi", "Swan Panasia Co., Ltd.", "Tilsit", "TRY SOFT" ],
	  "rank" : 147,
	  "thumbnail" : "//cf.geekdo-images.com/images/pic2419375_t.jpg",
	  "yearPublished" : 1995
	}

	this.getGames = function(base_url){
		return games;
	}

	this.getSingleGame = function(base_url,name){
		//game.image = game.image.substr(2,game.image.length);
		game.id = name;
		return game;
	}

	this.getAllGames = function(base_url){
		var games = this.getGames(base_url);
		var gamesDetail = [];
		for (id in games){
			gamesDetail.push(this.getSingleGame(base_url,games[id]));
		}
		return gamesDetail;
	}
}]);